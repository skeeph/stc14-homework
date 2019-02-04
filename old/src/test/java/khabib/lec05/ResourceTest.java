package khabib.lec05;

import khabib.lec05.loaders.ResourceLoader;
import khabib.lec05.utils.TestResultStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceTest {
    private TestResultStorage storage;
    private ByteArrayOutputStream errStream;
    private Set<String> words;

    @BeforeEach
    void setUp() {
        storage = new TestResultStorage();
        errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
        words = new HashSet<>(Arrays.asList("hello", "meow"));
    }

    @Test
    void parseResource() throws IOException {
        ResourceLoader rl = () -> new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream("Hello world.".getBytes())
        ));
        Resource res = new Resource(rl, words, storage);
        res.parseResource();
        assertEquals(1, storage.getSentences().size());
        assertEquals("Hello world.", storage.getSentences().get(0));
    }

    @Test
    void parseNextLineFinished() throws IOException {
        ResourceLoader rl = () -> new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream("Hello\nworld.".getBytes())
        ));
        Resource res = new Resource(rl, words, storage);
        res.parseResource();
        assertEquals(1, storage.getSentences().size());
        assertEquals("Hello world.", storage.getSentences().get(0));
    }

    @Test
    void multiLineSentences() throws IOException {
        ResourceLoader rl = () -> new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream("Hello\nmultiline\nworld.".getBytes())
        ));
        Resource res = new Resource(rl, words, storage);
        res.parseResource();
        assertEquals(1, storage.getSentences().size());
        assertEquals("Hello multiline world.", storage.getSentences().get(0));
    }

    @Test
    void testParseResourceException() {
        ResourceLoader rl = () -> {
            throw new IOException("mocked exception");
        };
        Resource res = new Resource(rl, words, storage);
        assertThrows(IOException.class, res::parseResource);
    }

    @Test
    void testRun() {
        ResourceLoader rl = () -> new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream("Hello world.".getBytes())
        ));
        Resource res = new Resource(rl, words, storage);
        res.run();
        assertEquals(1, storage.getSentences().size());
        assertEquals("Hello world.", storage.getSentences().get(0));
    }

    @Test
    void testRunException() {
        ResourceLoader rl = () -> {
            throw new IOException("mocked exception");
        };
        Resource res = new Resource(rl, words, storage);
        res.run();
        assertEquals(
                String.format("Ошибка парсинга ресурса %s: java.io.IOException: mocked exception", rl),
                errStream.toString()
        );
    }
}