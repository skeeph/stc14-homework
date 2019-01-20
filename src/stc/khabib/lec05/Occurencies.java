package stc.khabib.lec05;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Occurencies {
    private static final long TIMEOUT = 3600;
    Set<String> words;
    ExecutorService threadPool;

    public Occurencies() {
        this(5);
    }

    public Occurencies(int threadsCount) {
        this.threadPool = Executors.newFixedThreadPool(threadsCount);
    }

    public void getOccurencies(String[] sources, String[] words, String res) {
        this.words = new HashSet<>();
        this.words.addAll(Arrays.asList(words));


        try (ResultStorage storage = new ResultStorage(res)) {
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