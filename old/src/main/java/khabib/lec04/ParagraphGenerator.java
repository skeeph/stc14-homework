package khabib.lec04;

import java.util.Random;

/**
 * Генератор параграфов
 */
public class ParagraphGenerator implements Generator {
    private static final int MAX_SENTENCES_IN_PARAGRAPH = 20;
    private static final String PARAGRAPH_END = "\r\n";
    private final Random rnd;
    private SentenceGenerator sg;

    /**
     * Конструктор по умолчанию
     */
    public ParagraphGenerator() {
        this(new SentenceGenerator());
    }

    /**
     * Конструктор с заданием генератора предложение
     *
     * @param sg генератор предложений
     */
    public ParagraphGenerator(SentenceGenerator sg) {
        this.rnd = new Random();
        this.sg = sg;
    }

    /**
     * Генерирует случайный абзац текста
     *
     * @return следующий абзац текста
     */
    public String getNextGenerated() {
        StringBuilder paragraph = new StringBuilder();
        int paragraphSize = this.rnd.nextInt(MAX_SENTENCES_IN_PARAGRAPH - 1) + 1;
        for (int i = 0; i < paragraphSize; i++) {
            paragraph.append(this.sg.getNextGenerated());
        }
        paragraph.append(PARAGRAPH_END);
        return paragraph.toString();
    }

    /**
     * @return Строковое предсавление
     */
    @Override
    public String toString() {
        return "Paragraphs generator. Maximum length: " + MAX_SENTENCES_IN_PARAGRAPH + " sentences";
    }
}
