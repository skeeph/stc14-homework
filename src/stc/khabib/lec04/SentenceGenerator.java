package stc.khabib.lec04;

import stc.khabib.lec04.providers.WordsProvider;

import java.util.Random;

/**
 * Генератор целых предложений
 */
public class SentenceGenerator implements Generator {
    public static final int MAX_WORDS_IN_SENTENCE = 15;
    private Random rnd;
    private String[] words;
    private double probability = 0.5;

    /**
     * Конструктор по умолчанию. Использует вероятность выбора слова 0,5
     * и WordsGenerator для генерации словаря
     */
    SentenceGenerator() {
        this(0.5);
    }

    /**
     * Использует заданную вероятность выбора слова  и WordsGenerator для генерации словаря
     *
     * @param p вероятность вхождения слова в предложение
     */
    SentenceGenerator(double p) {
        this(new WordGenerator(), p);
    }

    /**
     * Конструктор использует заданный словарь для создания генератора предложений
     *
     * @param words провайдер слов для генерации предложений
     */
    SentenceGenerator(WordsProvider words) {
        this.rnd = new Random();
        this.words = words.getWords();
    }

    /**
     * @param words провайдер слов для генерации предложений
     * @param p     вероятность вхождения каждого слова в предложения
     */
    SentenceGenerator(WordsProvider words, double p) {
        this(words);
        this.probability = p;
    }

    /**
     * @return сгенерировать еще одно предложение
     */
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

    /**
     * @return символы для добавления конец предложения
     */
    private String getEndOfSentence() {
        String[] ends = new String[]{". ", "! ", "? "};
        return ends[rnd.nextInt(3)];
    }

    /**
     * @return разделитель слов в предложении
     */
    private String getWordDelimiter() {
        String[] ends = new String[]{" ", ", "};
        return ends[rnd.nextInt(2)];
    }

    /**
     * Функция превращает первую букву слова в заглавную
     *
     * @param word слово для преобразования
     * @return слово с заглавной буквы
     */
    private String capitalizeWord(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    /**
     * @return строковое представление
     */
    @Override
    public String toString() {
        return "Sentence Generator. Maximum length: " + MAX_WORDS_IN_SENTENCE + " words";
    }
}
