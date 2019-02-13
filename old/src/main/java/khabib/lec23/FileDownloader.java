package khabib.lec23;

import khabib.lec23.api.Downloader;

public class FileDownloader implements Downloader {
    @Override
    public Object download(String path) {
        System.out.printf("Загружаю файл из \"%s\"\n", path);
        return "Some Content";
    }
}
