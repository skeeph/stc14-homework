package stc.khabib.lec04.providers;

import java.util.Arrays;

/**
 * Обертка реализующая WordsProvider вокруг массивов
 */
public class ArrayProvider implements WordsProvider {
    private String[] words;

    public ArrayProvider(String[] words) {
        this.words = words;
    }

    /**
     * @return Все слова переданные в конструкторе
     */
    @Override
    public String[] getWords() {
        return this.words;
    }

    /**
     * @param count число слов для генерации текста
     * @return определенное число слов из массива
     */
    @Override
    public String[] getWords(int count) {
        if (this.words.length <= count) {
            return this.words;
        }
        return Arrays.copyOf(this.words, count);
    }
}