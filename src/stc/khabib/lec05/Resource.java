package stc.khabib.lec05;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Resource extends Thread {
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

    private final Pattern endSentencePattern;
    private final Pattern beginSentencePattern;
    private final Pattern sentencePattern;
    private String unfinishedSentence = "";

    private Set<String> targetWords;
    private ResultStorage storage;
    private BufferedReader reader;
    private String path;

    public Resource(String path, Set<String> targetWords, ResultStorage storage) {
        endSentencePattern = Pattern.compile(RE_END_SENTENCE);
        beginSentencePattern = Pattern.compile(RE_BEGIN_SENTENCE);
        sentencePattern = Pattern.compile(RE_SENTENCE, Pattern.MULTILINE);

        this.targetWords = targetWords;
        this.storage = storage;
        this.path = path;
    }

    private BufferedReader openResource() throws IOException {
        InputStream is;
        if (isURL(path)) {
            is = new URL(path).openStream();
        } else {
            is = new FileInputStream(path);
        }
        return new BufferedReader(new InputStreamReader(is));

    }

    public void parseResource() throws IOException {
        try (BufferedReader r = this.openResource()) {
            this.reader = r;
            String content;
            while ((content = this.reader.readLine()) != null) {
                this.checkLine(content);
            }
        } finally {
            this.reader.close();
            this.reader = null;
        }
        System.out.println("Умпешно обработан ресурс: " + this.path);
    }

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

    private boolean isMatch(String sentence) {
        return !Collections.disjoint(sentenceToWords(sentence), targetWords);
    }

    private Set<String> sentenceToWords(String sentence) {
        return Arrays.stream(
                sentence.split("\\s+")
        )
                .map(this::removeTrailingPunctuation)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    private String[] findMatches(Pattern pattern, String content) {
        List<String> matches = new LinkedList<>();
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            matches.add(matcher.group(0));
        }
        return matches.toArray(new String[0]);
    }

    private String removeTrailingPunctuation(String sentence) {
        return sentence.replaceAll("[^a-zA-Zа-яА-Я\\d\\s]", "");
    }

    private boolean isURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void run() {
        try {
            this.parseResource();
        } catch (IOException e) {
            System.err.printf("Ошибка парсинга ресурса %s: %s", this.path, e);
        }
    }
}
