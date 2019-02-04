package khabib.lec05.storage;

import java.io.IOException;

public interface StorageFactory {
    ResultStorage getStorage(String path) throws IOException;
}
