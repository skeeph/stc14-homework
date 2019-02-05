package khabib.lec05.loaders;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class URILoaderTest {
    public static final String content = "test\nload";
    private static final String url = "http://some.url/path/file.txt";
    private static URLConnection mockedConnection;

    @BeforeAll
    static void setUpAll() {
        URLStreamHandler mockedHandler = new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) {
                return mockedConnection;
            }
        };
        URL.setURLStreamHandlerFactory((protocol) -> protocol.equals("file") ? null : mockedHandler);
    }

    @BeforeEach
    void setUp() {
        mockedConnection = Mockito.mock(HttpURLConnection.class);
    }

    private Stream<String> lines(InputStream is) {
        return new BufferedReader(new InputStreamReader(is)).lines();
    }

    @Test
    void testLoadResourceFromURL() throws IOException {
        Mockito
                .when(mockedConnection.getInputStream())
                .thenReturn(new ByteArrayInputStream(URILoaderTest.content.getBytes()));

        ResourceLoader rl = new URILoader();
        InputStream is = rl.loadResource(url).get();
        String read = lines(is).collect(Collectors.joining("\n"));
        assertEquals(content, read);
    }

    @Test
    void testLoadException() throws IOException {
        Mockito
                .when(mockedConnection.getInputStream())
                .thenThrow(new IOException("some mocked exception"));
        ResourceLoader rl = new URILoader();
        assertThrows(IOException.class, () -> rl.loadResource(url));
    }

    @Test
    void testToString() {
        ResourceLoader loader = new URILoader();
        assertEquals("URILoader", loader.toString());
    }

    @Test
    void loadResourceFromFile() throws IOException {
        TemporaryFolder tf = new TemporaryFolder();
        tf.create();
        File file = tf.newFile();
        Files.write(Paths.get(file.toURI()), content.getBytes());
        ResourceLoader rl = new URILoader();
        Supplier<InputStream> supplier = rl.loadResource(file.getAbsolutePath());
        String read = lines(supplier.get()).collect(Collectors.joining("\n"));
        assertEquals(content, read);
        tf.delete();
    }
}