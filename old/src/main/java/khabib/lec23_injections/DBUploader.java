package khabib.lec23_injections;

import khabib.lec23_injections.api.Uploader;

public class DBUploader implements Uploader {
    @Override
    public boolean upload(String path, Object object) {
        System.out.printf("Выгружаю БД в \"%s\" объект \"%s\"\n", path, object);
        return true;
    }
}
