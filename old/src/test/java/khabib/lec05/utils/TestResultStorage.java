package khabib.lec05.utils;

import khabib.lec05.storage.ResultStorage;

import java.util.ArrayList;
import java.util.List;

public class TestResultStorage implements ResultStorage {

    private List<String> sentences;

    public TestResultStorage() {
        this.sentences = new ArrayList<>();
    }

    @Override
    public void writeSentence(String sentence) {
        sentences.add(sentence);
    }

    @Override
    public void close() {
    }

    public List<String> getSentences() {
        return sentences;
    }
}
