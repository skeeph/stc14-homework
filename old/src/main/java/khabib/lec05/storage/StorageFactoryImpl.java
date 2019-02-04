package khabib.lec05.storage;

import java.io.IOException;

public class StorageFactoryImpl implements StorageFactory {
    @Override
    public ResultStorage getStorage(String path) throws IOException {
        return new FileStorage(path);
    }
}
