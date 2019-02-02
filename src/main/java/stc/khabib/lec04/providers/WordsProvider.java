package stc.khabib.lec04.providers;

/**
 * Интерфейс для классов предоставляющих слова для генерациия текста
 */
public interface WordsProvider {
    /**
     * Получить все доступные слова.
     *
     * @return Массив слов
     */
    String[] getWords();

    /**
     * Получить запрошенное число слов
     *
     * @param count число слов для генерации текста
     * @return массив слов
     */
    String[] getWords(int count);

}

