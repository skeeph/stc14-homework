package khabib.lec23_injections;

import khabib.lec23_injections.api.Uploader;
import org.springframework.stereotype.Component;

@Component
public class FileUploader implements Uploader {
    @Override
    public boolean upload(String path, Object object) {
        System.out.printf("Выгружаю файл в \"%s\" объектс \"%s\"\n", path, object);
        return true;
    }
}
