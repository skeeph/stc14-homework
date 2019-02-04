package khabib.lec05;

import khabib.lec05.loaders.ResourceLoader;
import khabib.lec05.utils.TestResultStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OccurenciesTest {
    private TestResultStorage storage;
    private ByteArrayOutputStream errStream;

    @BeforeEach
    void setUp() {
        storage = new TestResultStorage();
        errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));
    }

    private Supplier<InputStream> getSupplier(String text) {
        return () -> new ByteArrayInputStream(text.getBytes());
    }

    @Test
    void getOccurencies() throws IOException {
        ResourceLoader rl = Mockito.mock(ResourceLoader.class);
        Mockito.when(rl.loadResource("one.txt")).thenReturn(getSupplier("Hello\nmy friend. Dou you burn?"));
        Mockito.when(rl.loadResource("second.txt")).thenReturn(getSupplier("Let the Galaxy burn! Gorka Morka."));
        Occurencies oc = new Occurencies(1, rl, (path) -> storage);
        String[] fileNames = new String[]{"one.txt", "second.txt", "fourth.txt"};
        oc.getOccurencies(fileNames, new String[]{"burn"}, "");
        assertEquals(2, storage.getSentences().size());
        assertArrayEquals(new String[]{"Dou you burn?", "Let the Galaxy burn!"}, storage.getSentences().toArray(new String[0]));
    }

    @Test
    void openFileException() throws IOException {
        ResourceLoader rl = Mockito.mock(ResourceLoader.class);
        Mockito.when(rl.loadResource(Mockito.anyString())).thenThrow(new IOException("file not found"));
        Occurencies oc = new Occurencies(1, rl, (path) -> storage);
        String[] fileNames = new String[]{"one.txt"};
        oc.getOccurencies(fileNames, new String[]{"burn"}, "");
        assertEquals(0, storage.getSentences().size());
        assertEquals("Произошла ошибка в процессе работы с ресурсами: java.io.IOException: file not found\n", errStream.toString());
    }
}