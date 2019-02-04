package khabib.lec05;

import khabib.lec05.loaders.ResourceLoader;
import khabib.lec05.loaders.URILoader;
import khabib.lec05.storage.ResultStorage;
import khabib.lec05.storage.StorageFactory;
import khabib.lec05.storage.StorageFactoryImpl;

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
    private ResourceLoader loader;
    private StorageFactory storageFactory;

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
        loader = new URILoader();
        this.storageFactory = new StorageFactoryImpl();
    }

    public Occurencies(int threadsCount, ResourceLoader loader, StorageFactory storageFactory) {
        this.threadPool = Executors.newFixedThreadPool(threadsCount);
        this.loader = loader;
        this.storageFactory = storageFactory;
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

        try (ResultStorage storage = this.storageFactory.getStorage(res)) {
            for (String source : sources) {
                Runnable resource = new Resource(this.loader.loadResource(source), this.words, storage);
                threadPool.submit(resource);
            }
            threadPool.shutdown();
            threadPool.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.err.println("Произошла ошибка в процессе работы с ресурсами: " + e);
        }
    }
}