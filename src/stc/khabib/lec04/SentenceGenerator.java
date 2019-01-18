package stc.khabib.lec04;

import java.util.Random;

public class SentenceGenerator implements Generator {
    public static final int MAX_WORDS_IN_SENTENCE = 15;
    private Random rnd;
    private String[] words;
    private double probability = 0.5;

    SentenceGenerator() {
        this(0.5);
    }

    SentenceGenerator(double p) {
        this.probability = p;
        this.rnd = new Random();
        this.words = new WordGenerator().generateWordsArray(100);
    }

    SentenceGenerator(String[] words) {
        this.rnd = new Random();
        this.words = words;
    }

    SentenceGenerator(String[] words, double p) {
        this(words);
        this.probability = p;
    }

    public String getNextGenerated() {
        StringBuilder sentence = new StringBuilder();
        int sentenceLength = this.rnd.nextInt(MAX_WORDS_IN_SENTENCE - 1) + 1;
        int numberWords = 0;

        for (int w = 0; w < words.length; w++) {
            String word = words[w];
            if (this.rnd.nextDouble() <= this.probability) {
                if (numberWords == 0) {
                    word = this.capitalizeWord(word);
                }
                sentence.append(word);
                numberWords++;
                if (numberWords != sentenceLength && w != words.length - 1) {
                    sentence.append(getWordDelimiter());
                }
            }
            if (numberWords >= sentenceLength) {
                break;
            }
        }
        sentence.append(this.getEndOfSentence());
        return sentence.toString();
    }

    private String getEndOfSentence() {
        String[] ends = new String[]{". ", "! ", "? "};
        return ends[rnd.nextInt(3)];
    }

    private String getWordDelimiter() {
        String[] ends = new String[]{" ", ", "};
        return ends[rnd.nextInt(2)];
    }

    private String capitalizeWord(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    @Override
    public String toString() {
        return "Sentence Generator. Maximum length: " + MAX_WORDS_IN_SENTENCE + " words";
    }
}
