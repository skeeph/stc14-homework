package stc.khabib.lec04;

import java.io.FileOutputStream;
import java.io.IOException;

public class FilesGenerator {
    public void getFiles(String path, int n, int size, String[] words, int probability) {
        SentenceGenerator sg = new SentenceGenerator(words, 1.0 / probability);
        ParagraphGenerator pg = new ParagraphGenerator(sg);

        for (int i = 1; i <= n; i++) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(path + "/" + i + ".txt")) {
                byte[] buf = null;
                for (int j = 0; j < size; j++) {
                    buf = pg.getNextGenerated().getBytes();
                    fileOutputStream.write(buf);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
