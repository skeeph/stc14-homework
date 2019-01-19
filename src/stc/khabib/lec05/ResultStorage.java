package stc.khabib.lec05;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ResultStorage implements AutoCloseable {
    BufferedWriter storage;

    public ResultStorage(String path) throws IOException {
        this.storage = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
        storage.write("");
        storage.close();

        this.storage = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
    }

    public void writeSentence(String sentence) throws IOException {
        this.storage.write(sentence);
        this.storage.newLine();
    }

    @Override
    public void close() throws Exception {
        this.storage.close();
    }
}

