package khabib.lec05.loaders;

import java.io.*;
import java.net.URL;

public class URILoader implements ResourceLoader {
    private final String path;

    public URILoader(String path) {
        this.path = path;
    }

    /**
     * Открытие ресурса
     *
     * @return объект для чтения из ресурса
     * @throws IOException ошибка открытия
     */
    @Override
    public BufferedReader loadResource() throws IOException {
        InputStream is;
        if (isURL(path)) {
            is = new URL(path).openStream();
        } else {
            is = new FileInputStream(path);
        }
        return new BufferedReader(new InputStreamReader(is));
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
        return "Resource{" + path + '}';
    }
}
