package stc.khabib.lec04;

import java.util.Random;

public class WordGenerator implements Generator {
    public static final int WORD_MAXLENGTH = 15;
    private Random rnd;
    private int maxLength;


    public WordGenerator() {
        this(WORD_MAXLENGTH);
    }

    public WordGenerator(int length) {
        this.maxLength = length;
        this.rnd = new Random();
    }

    public String[] generateWordsArray(int count) {
        String[] words = new String[count];
        for (int i = 0; i < count; i++) {
            words[i] = this.getNextGenerated();
        }
        return words;
    }

    public String getNextGenerated() {
        StringBuilder sb = new StringBuilder();
        int length = rnd.nextInt(this.maxLength - 1) + 1;
        for (int i = 0; i < length; i++) {
            sb.append((char) (rnd.nextInt(25) + 97));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Words Generator. Maximum length: " + this.maxLength + " chars";
    }
}

