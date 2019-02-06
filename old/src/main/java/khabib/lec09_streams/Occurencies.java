package khabib.lec09_streams;

import khabib.lec05.storage.FileStorage;
import khabib.lec05.storage.ResultStorage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс для поиска совпадений в ресурсах
 */
@SuppressWarnings("Duplicates")
public class Occurencies {
    Set<String> words;
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
            Arrays
                    .stream(sources).parallel()
                    .map((source) -> new Resource(source, this.words, storage))
                    .forEach(Resource::parseResource);
        } catch (Exception e) {
            System.err.println("Ошибка работы с хранилищем результатов: " + e);
        }
    }
}