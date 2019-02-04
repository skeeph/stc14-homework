package khabib.lec05.loaders;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class URILoaderTest {
    public static final String content = "test\nload";
    private static final String url = "http://some.url/path/file.txt";

    @BeforeAll
    static void setUp() {
        MockURLStreamHandler handler = new MockURLStreamHandler();
        URL.setURLStreamHandlerFactory(handler);
    }

    private Stream<String> lines(InputStream is) {
        return new BufferedReader(new InputStreamReader(is)).lines();
    }

    @Test
    void testLoadResourceFromURL() throws IOException {
        ResourceLoader rl = new URILoader();
        InputStream is = rl.loadResource(url).get();
        String read = lines(is).collect(Collectors.joining("\n"));
        assertEquals(content, read);
    }

    @Test
    void testLoadException() {
        ResourceLoader rl = new URILoader();
        assertThrows(IOException.class, () -> rl.loadResource(url + ".eof"));
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
        String read = lines(rl.loadResource(file.getAbsolutePath()).get()).collect(Collectors.joining("\n"));
        assertEquals(content, read);
        tf.delete();
    }
}

class MockURLStreamHandler extends URLStreamHandler implements URLStreamHandlerFactory {
    @Override
    protected URLConnection openConnection(URL u) {
        return new MockHttpConnection(u);
    }

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        return this;
    }
}

class MockHttpConnection extends HttpURLConnection {

    /**
     * Constructor for the HttpURLConnection.
     *
     * @param u the url
     */
    protected MockHttpConnection(URL u) {
        super(u);
    }

    @Override
    public void disconnect() {
    }

    @Override
    public InputStream getInputStream() throws IOException {
        String[] fileParts = this.url.getFile().split("\\.");
        String end = fileParts[fileParts.length - 1];
        if ("eof".equals(end)) {
            throw new IOException("some mocked exception");
        }
        return new ByteArrayInputStream(URILoaderTest.content.getBytes());
    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() {
    }
}