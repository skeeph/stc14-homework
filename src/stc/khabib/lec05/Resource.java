package stc.khabib.lec05;

import stc.khabib.lec05.storage.ResultStorage;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс реализует задачу поиска предложений в тексте.
 */
public class Resource implements Runnable {
    /**
     * Регулярка, выбирает часть неоконченного предложения с конца строки.
     */
    private static final String RE_END_SENTENCE = "[A-ZА-Я][^!?.]+$";
    /**
     * Регулярка, выбирает из начала строки окончание предложения,
     * начатого на прошлой строке в ресурсе.
     */
    private static final String RE_BEGIN_SENTENCE = "^[^A-ZА-Я!?.]+[!?.]";

    /**
     * Регулярка, вытаскивает предложение из строки
     */
    private static final String RE_SENTENCE = "[A-ZА-Я][^!?.]+[!?.]";

    private final Pattern endSentencePattern = Pattern.compile(RE_END_SENTENCE);
    private final Pattern beginSentencePattern = Pattern.compile(RE_BEGIN_SENTENCE);
    private final Pattern sentencePattern = Pattern.compile(RE_SENTENCE, Pattern.MULTILINE);
    private String unfinishedSentence = "";

    private Set<String> targetWords;
    private ResultStorage storage;
    private BufferedReader reader;
    private String path;

    /**
     * @param path        Путь к ресурсу
     * @param targetWords Слова, которые нужно искать в предложениях
     * @param storage     хранилище результатов
     */
    public Resource(String path, Set<String> targetWords, ResultStorage storage) {
        this.targetWords = targetWords;
        this.storage = storage;
        this.path = path;
    }

    /**
     * Открытие ресурса
     *
     * @return объект для чтения из ресурса
     * @throws IOException ошибка открытия
     */
    private BufferedReader openResource() throws IOException {
        InputStream is;
        if (isURL(path)) {
            is = new URL(path).openStream();
        } else {
            is = new FileInputStream(path);
        }
        return new BufferedReader(new InputStreamReader(is));

    }

    /**
     * Парсинг ресурса и поиск подходящих предлоежний
     *
     * @throws IOException Ошибка чтения
     */
    public void parseResource() throws IOException {
        long startTime = System.nanoTime();
        try (BufferedReader r = this.openResource()) {
            this.reader = r;
            String content;
            int lineNum = 0;
            while ((content = this.reader.readLine()) != null) {
                this.checkLine(content);
                lineNum++;
                if (lineNum % 100000 == 0) {
                    System.err.println("====>" + this.path + " " + lineNum);
                }
            }
        } finally {
            this.reader.close();
            this.reader = null;
        }
        long elpsed = System.nanoTime() - startTime;
        System.out.println("SUCCESS: " + this.path + ". Time: " + elpsed / 1000000 + " ms.");
    }

    /**
     * Проверка строки текста.
     * <p>
     * Функция ищет предложения в тексте, проверяет на совпадения слов и искомыми
     * и записывает подходящие предложения в хранилище.
     *
     * @param line строка текста
     * @throws IOException ошибка
     */
    private void checkLine(String line) throws IOException {
        if (!unfinishedSentence.equals("")) {
            String[] continuations = findMatches(beginSentencePattern, line);
            if (continuations.length == 0) {
                unfinishedSentence += line;
                return;
            }

            String continuation = continuations[0];
            String sentence = unfinishedSentence + " " + continuation;
            if (isMatch(sentence)) {
                this.storage.writeSentence(sentence);
            }
            unfinishedSentence = "";
        }

        for (String matched : findMatches(sentencePattern, line)) {
            if (isMatch(matched)) {
                this.storage.writeSentence(matched);
            }
        }

        String[] end = findMatches(endSentencePattern, line);
        if (end.length != 0) {
            unfinishedSentence = end[0];
        }
    }

    /**
     * Функция проверяет содержатся ли в данной предложении хоть одно из искомых слов
     *
     * @param sentence Предложение
     * @return true если содержится
     */
    private boolean isMatch(String sentence) {
        return !Collections.disjoint(sentenceToWords(sentence), targetWords);
    }

    /**
     * Разбивает предложение по словам, удаляет пунктуацию и переводит все в нижний регистр
     *
     * @param sentence предложение
     * @return множество слов в предложении
     */
    private Set<String> sentenceToWords(String sentence) {
        Set<String> words = new HashSet<>();
        Collections.addAll(words, removeTrailingPunctuation(sentence.toLowerCase()).split("\\s+"));
        return words;
    }

    /**
     * Поиск совпадений в предложении по регулярке
     *
     * @param pattern регулярное выражения для поиска
     * @param content текст
     * @return массив совпадений
     */
    private String[] findMatches(Pattern pattern, String content) {
        List<String> matches = new LinkedList<>();
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            matches.add(matcher.group(0));
        }
        return matches.toArray(new String[0]);
    }

    /**
     * Удаляет пунктуацию из строки
     *
     * @param sentence строка текста
     * @return текста без пунктуации
     */
    private String removeTrailingPunctuation(String sentence) {
        return sentence.replaceAll("[^a-zA-Zа-яА-Я\\d\\s]", "");
    }

    /**
     * Функция проверяет является ли адрес ресурса путем к файлу или удаленному ресурсу
     *
     * @param url адрес
     * @return true - если адрес удаленного ресурса
     */
    private boolean isURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Запуск треда
     */
    @Override
    public void run() {
        try {
            this.parseResource();
        } catch (IOException e) {
            System.err.printf("Ошибка парсинга ресурса %s: %s", this.path, e);
        }
    }
}
