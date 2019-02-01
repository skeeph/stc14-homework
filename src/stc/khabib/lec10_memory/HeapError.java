package stc.khabib.lec10_memory;

import java.util.UUID;

public class HeapError {
    private static final int LOOP_COUNT = 100_000_000;

    public static void main(String[] args) throws InterruptedException {
        StringBuilder sb = new StringBuilder();

//        Random random = new Random();
//        for (int i = 0; i < LOOP_COUNT; i++) {
//            String str = "" + random.nextInt();
//            sb.append(str);
//            Thread.sleep(1);
//        }

        UUID uuid = new UUID(1, 2);
        uuid.toString();
        while (true) {
            sb.append("lolololo");
        }
//        System.out.println(sb.toString());
    }
}
