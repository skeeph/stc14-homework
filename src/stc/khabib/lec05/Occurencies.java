package stc.khabib.lec05;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Occurencies {
    Set<String> words;

    public void getOccurencies(String[] sources, String[] words, String res) throws IOException {
        this.words = new HashSet<>();
        this.words.addAll(Arrays.asList(words));

        for (String source : sources)
            try (BufferedReader br = openResourceReader(source)) {
                Resource resource = new Resource(br, this.words);
            }
    }

    private BufferedReader openResourceReader(String source) throws IOException {
        InputStream is = getResourceStream(source);
        return new BufferedReader(new InputStreamReader(is));

    }

    private InputStream getResourceStream(String source) throws IOException {
        if (isURL(source)) {
            return new URL(source).openStream();
        }
        return new FileInputStream(source);
    }

    private boolean isURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}