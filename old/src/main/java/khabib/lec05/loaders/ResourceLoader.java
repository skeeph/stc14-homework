package khabib.lec05.loaders;

import java.io.BufferedReader;
import java.io.IOException;

public interface ResourceLoader {
    BufferedReader loadResource() throws IOException;
}
