package stc.khabib.lec09_streams;

import stc.khabib.lec05.GuttenberGenerator;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class OccurenciesTest {
    public static void main(String[] args) throws IOException {
        System.out.println("Start");
        Occurencies oc = new Occurencies(10);
        long startTime = System.nanoTime();
        oc.getOccurencies(
//                new String[]{
//                        "./generated/0.txt",
//                        "https://www.ietf.org/rfc/rfc2616.txt",
//                        "./generated/war.txt",
//                        "./generated/lib.txt",
//                        "./generated/guttenberg.txt",
//                        "./generated/gut.txt",
//                },
                GuttenberGenerator.getResources(10),
                new String[]{"эртугрул", "текфур", "canonical", "age", "марию", "павловна", "participation", "bedford"},
                "found.txt"
        );
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        System.out.println("Elapsed time for all resources: " + elapsed + " ms.");
    }
}
