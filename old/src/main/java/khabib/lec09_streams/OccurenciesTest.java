package khabib.lec09_streams;

import khabib.lec05.GuttenberGenerator;

@SuppressWarnings("Duplicates")
public class OccurenciesTest {
    public static void main(String[] args) {
        System.out.println("Start");
        Occurencies oc = new Occurencies();
        long startTime = System.nanoTime();
        oc.getOccurencies(
                GuttenberGenerator.getResources(10),
                new String[]{"participation", "bedford"},
                "found.txt"
        );
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        System.out.println("Elapsed time for all resources: " + elapsed + " ms.");
    }
}
