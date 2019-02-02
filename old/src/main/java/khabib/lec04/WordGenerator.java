package khabib.lec04;

import khabib.lec04.providers.WordsProvider;

import java.util.Random;

/**
 * Генератор случайных слов
 */
public class WordGenerator implements Generator, WordsProvider {
    public static final int WORD_MAXLENGTH = 15;
    private Random rnd;
    private int maxLength;


    /**
     * Коснтруктор по умолчанию. Созданный объект генерирует слов длинной от 1 до 15
     */
    public WordGenerator() {
        this(WORD_MAXLENGTH);
    }

    /**
     * Объект созданный этом конструктором генерирует слова длиной от 1 до length
     *
     * @param length максимальная длина слова, генерируемого объектом
     */
    public WordGenerator(int length) {
        this.maxLength = length;
        this.rnd = new Random();
    }

    /**
     * @return следующее случайное слово
     */
    public String getNextGenerated() {
        StringBuilder sb = new StringBuilder();
        int length = rnd.nextInt(this.maxLength - 1) + 1;
        for (int i = 0; i < length; i++) {
            sb.append((char) (rnd.nextInt(25) + 97));
        }
        return sb.toString();
    }

    /**
     * @return строковое представление
     */
    @Override
    public String toString() {
        return "Words Generator. Maximum length: " + this.maxLength + " chars";
    }

    /**
     * Т.к. мы можем сгенерировать бесконечное число случаных наборов символов,
     * в функции возвращающей все слова, мы возвращаем 1000 случайных слов
     *
     * @return массив слов
     */
    @Override
    public String[] getWords() {
        return this.getWords(1000);
    }

    /**
     * Генерирует и возвращает заданное число случайных слов
     *
     * @param count число слов для генерации текста
     * @return массив слов
     */
    @Override
    public String[] getWords(int count) {
        String[] words = new String[count];
        for (int i = 0; i < count; i++) {
            words[i] = this.getNextGenerated();
        }
        return words;
    }
}

