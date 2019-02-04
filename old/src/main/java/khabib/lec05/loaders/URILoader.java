package khabib.lec05.loaders;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        if (isURL(path)) {
            is = new URL(path).openStream();
        } else {
            is = new FileInputStream(path);
        }
        return () -> is;
    }

    /**
     * Функция проверяет является ли адрес ресурса путем к файлу или удаленному ресурсу
     *
     * @param url адрес
     * @return true - если адрес удаленного ресурса
     */
    private boolean isURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "URILoader";
    }
}
