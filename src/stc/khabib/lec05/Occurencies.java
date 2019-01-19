package stc.khabib.lec05;

import java.util.*;

public class Occurencies {
    Set<String> words;

    public void getOccurencies(String[] sources, String[] words, String res) {
        this.words = new HashSet<>();
        this.words.addAll(Arrays.asList(words));

        List<Thread> threads = new LinkedList<>();

        try (ResultStorage storage = new ResultStorage(res)) {
            for (String source : sources) {
                Thread resource = new Resource(source, this.words, storage);
                resource.start();
                threads.add(resource);
            }

            for (Thread thread : threads) {
                thread.join();
            }
        } catch (Exception e) {
            System.err.println("Ошибка работы с хранилищем результатов: " + e);
        }
    }
}