package khabib.lec05;

import khabib.lec05.utils.TestResultStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

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
        Supplier<InputStream> rl = () -> new ByteArrayInputStream("Hello world.".getBytes());

        Resource res = new Resource(rl, words, storage);
        res.parseResource();
        assertEquals(1, storage.getSentences().size());
        assertEquals("Hello world.", storage.getSentences().get(0));
    }

    @Test
    void parseNextLineFinished() throws IOException {
        Supplier<InputStream> rl = () -> new ByteArrayInputStream("Hello\nworld.".getBytes());
        Resource res = new Resource(rl, words, storage);
        res.parseResource();
        assertEquals(1, storage.getSentences().size());
        assertEquals("Hello world.", storage.getSentences().get(0));
    }

    @Test
    void multiLineSentences() throws IOException {
        Supplier<InputStream> rl = () ->
                new ByteArrayInputStream("Hello\nmultiline\nworld.".getBytes());
        Resource res = new Resource(rl, words, storage);
        res.parseResource();
        assertEquals(1, storage.getSentences().size());
        assertEquals("Hello multiline world.", storage.getSentences().get(0));
    }

    @Test
    void testParseResourceException() {
        Supplier<InputStream> rl = () -> {
            throw new RuntimeException("mocked exception");
        };
        Resource res = new Resource(rl, words, storage);
        assertThrows(RuntimeException.class, res::parseResource);
    }

    @Test
    void testRun() {
        Supplier<InputStream> rl = () -> new ByteArrayInputStream("Hello world.".getBytes());
        Resource res = new Resource(rl, words, storage);
        res.run();
        assertEquals(1, storage.getSentences().size());
        assertEquals("Hello world.", storage.getSentences().get(0));
    }

    @Test
    void testRunException() {
        Supplier<InputStream> rl = () -> {
            throw new RuntimeException("mocked exception");
        };
        Resource res = new Resource(rl, words, storage);
        res.run();
        assertEquals(
                String.format("Ошибка парсинга ресурса %s: java.lang.RuntimeException: mocked exception", rl),
                errStream.toString()
        );
    }
}