package khabib.lec10_memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс вызывает переполнение кучи.
 */
public class HeapError {
    private static final int LOOP_COUNT = 100_000_000;

    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<>();


        Random random = new Random();
        for (int i = 0; i < LOOP_COUNT; i++) {
            String str = "" + random.nextInt();
            list.add(str);
            Thread.sleep(1);
        }

        System.out.println(list.size());
    }
}
