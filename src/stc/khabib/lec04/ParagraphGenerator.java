package stc.khabib.lec04;

import java.util.Random;

public class ParagraphGenerator implements Generator {
    private static final int MAX_SENTENCES_IN_PARAGRAPH = 20;
    private static final String PARAGRAPH_END = "\r\n";
    private final Random rnd;
    private SentenceGenerator sg;

    public ParagraphGenerator() {
        this(new SentenceGenerator());
    }

    public ParagraphGenerator(SentenceGenerator sg) {
        this.rnd = new Random();
        this.sg = sg;
    }

    public String getNextGenerated() {
        StringBuilder paragraph = new StringBuilder();
        int paragraphSize = this.rnd.nextInt(MAX_SENTENCES_IN_PARAGRAPH - 1) + 1;
        for (int i = 0; i < paragraphSize; i++) {
            paragraph.append(this.sg.getClass());
        }
        paragraph.append(PARAGRAPH_END);
        return paragraph.toString();
    }

    @Override
    public String toString() {
        return "Paragraphs generator. Maximum length: " + MAX_SENTENCES_IN_PARAGRAPH + " sentences";
    }
}
