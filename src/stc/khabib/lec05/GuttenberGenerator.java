package stc.khabib.lec05;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GuttenberGenerator {
    private static final int MAX = 19600;
    private static final String urlTemplate = "http://www.gutenberg.org/cache/epub/%d/pg%d.txt";

    public static String[] getResources(int count) {
        List<String> url = new LinkedList<>();
        Random rnd = new Random();
        for (int i = 0; i < count; i++) {
            int nextBook = rnd.nextInt(MAX - 1) + 1;
            url.add(String.format(urlTemplate, nextBook, nextBook));
        }
        return url.toArray(new String[0]);
    }

}
