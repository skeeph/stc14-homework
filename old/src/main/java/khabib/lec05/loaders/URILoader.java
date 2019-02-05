package khabib.lec05.loaders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Supplier;

public class URILoader implements ResourceLoader {
    /**
     * Открытие ресурса
     *
     * @return объект для чтения из ресурса
     * @throws IOException ошибка открытия
     */
    @Override
    public Supplier<InputStream> loadResource(String path) throws IOException {
        InputStream is;
        is = toURL(path).openStream();
        return () -> is;
    }

    /**
     * Функция проверяет является ли адрес ресурса путем к файлу или удаленному ресурсу
     *
     * @param path адрес
     * @return URL к ресурсу
     */
    private URL toURL(String path) throws IOException {
        URL url;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            url = new File(path).toURI().toURL();
        }
        return url;
    }

    @Override
    public String toString() {
        return "URILoader";
    }
}
