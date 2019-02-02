package khabib.lec05.storage;

import java.io.IOException;

/**
 * Хранилище предложений, в которых есть искомые слова
 */
public interface ResultStorage extends AutoCloseable {
    /**
     * Записывает предложение в хранилище
     *
     * @param sentence предложение
     * @throws IOException ошибка записи
     */
    void writeSentence(String sentence) throws IOException;
}
