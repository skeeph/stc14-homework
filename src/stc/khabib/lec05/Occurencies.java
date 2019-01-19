package stc.khabib.lec05;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Occurencies {
    Set<String> words;

    public void getOccurencies(String[] sources, String[] words, String res) {
        this.words = new HashSet<>();
        this.words.addAll(Arrays.asList(words));

        try (ResultStorage storage = new ResultStorage(res)) {
            for (String source : sources) {
                try (Resource resource = new Resource(source, this.words, storage)) {
                    resource.parseResource();
                } catch (IOException e) {
                    System.err.println("Ошибка открытия ресурса" + e);
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка работы с хранилищем результатов: " + e);
        }
    }
}