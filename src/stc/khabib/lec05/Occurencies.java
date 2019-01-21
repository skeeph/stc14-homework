package stc.khabib.lec05;

import stc.khabib.lec05.storage.FileStorage;
import stc.khabib.lec05.storage.ResultStorage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Класс для поиска совпадений в ресурсах
 */
public class Occurencies {
    private static final long TIMEOUT = 3600;
    Set<String> words;
    ExecutorService threadPool;

    /**
     * Конструктор по умолчанию. Число потоков 5.
     */
    public Occurencies() {
        this(5);
    }

    /**
     * Конструктор с заданным число потоков
     *
     * @param threadsCount число потоков
     */
    public Occurencies(int threadsCount) {
        this.threadPool = Executors.newFixedThreadPool(threadsCount);
    }

    /**
     * Основная функция.
     *
     * @param sources список адресов ресурсов
     * @param words   список искомых слов
     * @param res     адрес файла куда записывать найденные предложения
     */
    public void getOccurencies(String[] sources, String[] words, String res) {
        this.words = new HashSet<>();
        this.words.addAll(Arrays.asList(words));


        try (ResultStorage storage = new FileStorage(res)) {
            for (String source : sources) {
                Runnable resource = new Resource(source, this.words, storage);
                threadPool.submit(resource);
            }
            threadPool.shutdown();
            threadPool.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.err.println("Ошибка работы с хранилищем результатов: " + e);
        }
    }
}