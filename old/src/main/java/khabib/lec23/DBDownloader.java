package khabib.lec23;

import khabib.lec23.api.Downloader;

public class DBDownloader implements Downloader {
    @Override
    public Object download(String path) {
        System.out.printf("Загружаю файл из БД \"%s\"\n", path);
        return "Some DB Content";
    }
}
