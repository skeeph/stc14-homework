package khabib.lec23;

import khabib.lec23.api.Uploader;

public class DBUploader implements Uploader {
    @Override
    public boolean upload(String path, Object object) {
        System.out.printf("Выгружаю БД в \"%s\" объект \"%s\"\n", path, object);
        return true;
    }
}
