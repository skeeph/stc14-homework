package stc.khabib.lec04.providers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Создает словарь слов с html страницы
 */
public class WebPageWordsProvider implements WordsProvider {
    private Set<String> words;

    /**
     * Конструктор скачивает страницу, выбирает оттуда слова состоящие только из латинских и русских букв,
     * приводит их в нижний регистр и сохраяет в список.
     *
     * @param url адрес html-страницы
     * @throws IOException возникшая ошибка
     */
    public WebPageWordsProvider(String url) throws IOException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader buffered = new BufferedReader(new InputStreamReader(is));
            this.words = buffered.lines()
                    .map((x -> x.split(" ")))
                    .flatMap(Arrays::stream)
                    .filter(this::isAlpha)
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());
        }
    }


    /**
     * @return Все слова встретившиеся на странице
     */
    @Override
    public String[] getWords() {
        return this.words.toArray(new String[0]);
    }

    /**
     * @param count число слов для генерации текста
     * @return N слов со страницы
     */
    @Override
    public String[] getWords(int count) {
        return this.words.stream()
                .limit(count)
                .toArray(String[]::new);
    }

    /**
     * Проверка корректности слова
     *
     * @param s слово для проверки
     * @return состоит ли это слово только из букв
     */
    public boolean isAlpha(String s) {
        return s.matches("[a-zA-Zа-яА-Я]+");
    }
}
