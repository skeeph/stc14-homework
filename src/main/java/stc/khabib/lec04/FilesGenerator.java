package stc.khabib.lec04;

import stc.khabib.lec04.providers.ArrayProvider;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Генератор текстовые файлы с сгенерированным текстом
 */
public class FilesGenerator {
    /**
     * Создает текстовые файлы
     *
     * @param path        путь для сохранения файлов
     * @param n           сколько файлов необходимо создать
     * @param size        число абзацев в каждом файле
     * @param words       массив слов испольуземых для генерации
     * @param probability вероятность вхождения каждого слова в предложение
     */
    public void getFiles(String path, int n, int size, String[] words, int probability) {
        SentenceGenerator sg = new SentenceGenerator(new ArrayProvider(words), 1.0 / probability);
        ParagraphGenerator pg = new ParagraphGenerator(sg);

        for (int i = 1; i <= n; i++) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(path + "/" + i + ".txt")) {
                byte[] buf;
                for (int j = 0; j < size; j++) {
                    buf = pg.getNextGenerated().getBytes();
                    fileOutputStream.write(buf);
                }
            } catch (IOException e) {
                System.err.println("Произошла ошибка записи в файлы: " + e);
            }
        }
    }
}
