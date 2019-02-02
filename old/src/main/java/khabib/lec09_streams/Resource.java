package khabib.lec09_streams;

import khabib.lec05.storage.ResultStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Класс реализует задачу поиска предложений в тексте.
 */
@SuppressWarnings("Duplicates")
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

    private Set<String> targetWords;
    private ResultStorage storage;
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
    private Stream<String> openResourceStream() throws IOException {
        if (isURL(path)) {
            return new BufferedReader(
                    new InputStreamReader(new URL(path).openStream())
            ).lines();
        } else {
            return Files.lines(Paths.get(this.path));
        }

    }

    /**
     * Парсинг ресурса и поиск подходящих предлоежний
     *
     * @throws IOException Ошибка чтения
     */
    public void parseResource() throws IOException {
        long startTime = System.nanoTime();
        try (Stream<String> r = this.openResourceStream()) {
            Stream<String> collected = r.map(String::trim)
                    .map(this::checkLine)
                    .flatMap(Arrays::stream);
            collected.reduce("", (a, b) -> {
                String c = (a + " " + b).trim();
                if (sentencePattern.matcher(c).matches()) {
                    if (isMatch(c)) {
                        try {
                            this.storage.writeSentence(c);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return "";
                }
                return c;
            });
        }
        long elpsed = System.nanoTime() - startTime;
        System.out.println("SUCCESS: " + this.path + ". Time: " + elpsed / 1000000 + " ms.");
    }

    /**
     * Проверка предложений из строки с помощью стримов
     *
     * @param line строка для проверки
     * @return Массив незаконченных частей предложения
     */
    private String[] checkLine(String line) {
        List<String> unfinished = new LinkedList<>();
        List<String> begin = this.findMatches(beginSentencePattern, line);
        List<String> end = this.findMatches(endSentencePattern, line);
        List<String> sentences = this.findMatches(sentencePattern, line);
        if (begin.size() == 0 && end.size() == 0 && sentences.size() == 0) {
            unfinished.add(line);
        } else {
            unfinished.addAll(begin);
            unfinished.addAll(end);
            sentences.stream()
                    .filter(this::isMatch)
                    .forEach(x -> {
                        try {
                            this.storage.writeSentence(x);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
        return unfinished.toArray(new String[0]);
    }

    /**
     * Функция проверяет содержатся ли в данной предложении хоть одно из искомых слов
     *
     * @param sentence Предложение
     * @return true если содержится
     */
    private boolean isMatch(String sentence) {
        return !Collections.disjoint(
                sentenceToWords(removeTrailingPunctuation(sentence)),
                targetWords
        );
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
    private List<String> findMatches(Pattern pattern, String content) {
        List<String> matches = new LinkedList<>();
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            matches.add(matcher.group(0));
        }
        return matches;
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
