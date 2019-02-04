package khabib.lec05.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

@FunctionalInterface
public interface ResourceLoader {
    Supplier<InputStream> loadResource(String path) throws IOException;
}
