package khabib.lec04;

public class GeneratorTest {
    public static void main(String[] args) {
        new FilesGenerator().getFiles("./generated", 3, 10, new String[]{
                "hello", "my", "awesome", "word", "who", "doctor"
        }, 1);
    }
}
