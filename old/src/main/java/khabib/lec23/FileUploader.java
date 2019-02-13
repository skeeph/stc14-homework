package khabib.lec23;

import khabib.lec23.api.Uploader;

public class FileUploader implements Uploader {
    @Override
    public boolean upload(String path, Object object) {
        System.out.printf("Выгружаю файл в \"%s\" объектс \"%s\"\n", path, object);
        return true;
    }
}
