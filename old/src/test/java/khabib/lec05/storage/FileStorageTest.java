package khabib.lec05.storage;

import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageTest {
    @Rule
    static TemporaryFolder tf = new TemporaryFolder();
    File tempFile = null;

    @BeforeAll
    static void setUpAll() throws IOException {
        tf.create();
    }

    @AfterAll
    static void tearDownAll() {
        tf.delete();
    }

    @BeforeEach
    void setUp() throws IOException {
        tempFile = tf.newFile();
    }

    @AfterEach
    void tearDown() {
        tempFile = null;
    }

    @Test
    void close() {
        Exception ex = null;
        try {
            FileStorage fs = new FileStorage(tempFile.getAbsolutePath());
            fs.close();
        } catch (Exception e) {
            ex = e;
        }
        assertNull(ex);
    }

    @Test
    void storageCreationError() {
        assertThrows(IOException.class, () -> new FileStorage(""));
    }

    @Test
    void writeSentence() throws Exception {
        FileStorage fs = new FileStorage(tempFile.getAbsolutePath());
        fs.writeSentence("lorem ipsum");
        fs.writeSentence("dolor sit amet");
        fs.close();

        List<String> lines = Files.lines(Paths.get(tempFile.toURI()))
                .map(String::trim)
                .collect(Collectors.toList());
        assertEquals(2, lines.size());
        assertArrayEquals(new String[]{"lorem ipsum", "dolor sit amet"}, lines.toArray(new String[0]));
    }

    @Test
    void writeSentenceException() throws Exception {
        FileStorage fs = new FileStorage(tempFile.getAbsolutePath());
        fs.close();
        assertThrows(IOException.class, () -> fs.writeSentence("lorem ipsum"));
    }
}