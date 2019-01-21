package stc.khabib.lec05.storage;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Хранилище записывает предложения в файл
 */
public class FileStorage implements ResultStorage {
    BufferedWriter storage;

    /**
     * @param path путь к файлу для записи
     * @throws IOException ошибка открытия файла
     */
    public FileStorage(String path) throws IOException {
        this.storage = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
        storage.write("");
        storage.close();

        this.storage = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
    }

    public synchronized void writeSentence(String sentence) throws IOException {
        this.storage.write(sentence + "\n");
    }

    /**
     * Закрыть файл
     *
     * @throws Exception ошибка закрытия
     */
    @Override
    public void close() throws Exception {
        this.storage.close();
    }
}

