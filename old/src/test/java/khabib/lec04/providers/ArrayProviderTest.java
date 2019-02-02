package khabib.lec04.providers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ArrayProviderTest {
    String[] words;
    WordsProvider wp;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        words = new String[]{"first", "second", "third", "fourth", "fifth"};
        wp = new ArrayProvider(words);
    }

    @org.junit.jupiter.api.Test
    void getWords() {
        assertArrayEquals(words, wp.getWords(), "Не вернул все слова");
    }

    @org.junit.jupiter.api.Test
    void getWordsByCount() {
        assertArrayEquals(new String[]{"first", "second"}, wp.getWords(2));
        assertArrayEquals(words, wp.getWords(10));
    }
}