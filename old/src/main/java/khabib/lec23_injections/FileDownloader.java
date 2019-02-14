package khabib.lec23_injections;

import khabib.lec23_injections.api.Downloader;

public class FileDownloader implements Downloader {
    @Override
    public Object download(String path) {
        System.out.printf("Загружаю файл из \"%s\"\n", path);
        return "Some Content";
    }
}
