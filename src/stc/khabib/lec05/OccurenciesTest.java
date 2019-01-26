package stc.khabib.lec05;

import java.io.IOException;

public class OccurenciesTest {
    public static void main(String[] args) throws IOException {
        System.in.read();
        System.out.println("Start");
        Occurencies oc = new Occurencies(10);
        long startTime = System.nanoTime();
        oc.getOccurencies(
                new String[]{
                        "./generated/1.txt",
//                        "https://www.ietf.org/rfc/rfc2616.txt",
//                        "./generated/war.txt",
//                        "./generated/lib.txt",
//                        "./generated/guttenberg.txt",
//                        "./generated/gut.txt",
                },
//                GuttenberGenerator.getResources(100),
                new String[]{"эртугрул", "текфур", "canonical", "age", "марию", "павловна", "participation", "bedford"},
                "found.txt"
        );
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        System.out.println("Elapsed time for all resources: " + elapsed + " ms.");
        System.in.read();
    }
}
