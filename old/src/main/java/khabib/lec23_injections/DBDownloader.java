package khabib.lec23_injections;

import khabib.lec23_injections.api.Downloader;
import org.springframework.stereotype.Component;

@Component
public class DBDownloader implements Downloader {
    @Override
    public Object download(String path) {
        System.out.printf("Загружаю файл из БД \"%s\"\n", path);
        return "Some DB Content";
    }
}
