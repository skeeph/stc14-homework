package khabib.lec05;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GuttenberGeneratorTest {
    public static final String regex = "http://www\\.gutenberg\\.org/cache/epub/\\d{1,5}/pg\\d{1,5}\\.txt";

    @Test
    void getResourcesLength() {
        String[] links = GuttenberGenerator.getResources(10);
        assertEquals(10, links.length);

        links = GuttenberGenerator.getResources(0);
        assertEquals(0, links.length);

        links = GuttenberGenerator.getResources(-1);
        assertEquals(0, links.length);
    }

    @Test
    void getResourcesLink() {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        String[] links = GuttenberGenerator.getResources(10);
        assertTrue(Arrays.stream(links).allMatch(x -> pattern.matcher(x).matches()));
    }
}