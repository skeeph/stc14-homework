package stc.khabib.lec05;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Resource {
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

    public Resource(BufferedReader br, Set<String> targetWords) throws IOException {
        endSentencePattern = Pattern.compile(RE_END_SENTENCE);
        beginSentencePattern = Pattern.compile(RE_BEGIN_SENTENCE);
        sentencePattern = Pattern.compile(RE_SENTENCE, Pattern.MULTILINE);

        this.targetWords = targetWords;


        String content;
        while ((content = br.readLine()) != null) {
            checkLine(content);
        }

    }

    private void checkLine(String line) {
        if (!unfinishedSentence.equals("")) {
            String[] continuations = findMatches(beginSentencePattern, line);
            if (continuations.length == 0) {
                unfinishedSentence += line;
                return;
            }

            String continuation = continuations[0];
            String sentence = unfinishedSentence + " " + continuation;
            if (isMatch(sentence)) {
                System.out.println("MATCH => " + sentence);
            }
            unfinishedSentence = "";
        }

        for (String matched : findMatches(sentencePattern, line)) {
            if (isMatch(matched)) {
                System.out.println("MATCH => " + matched);
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
//        System.err.println(sentence);
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
}
