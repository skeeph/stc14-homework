package stc.khabib.lec3;

import java.util.List;

public class BoxTester {
    public static void main(String[] args) {
        MathBox<Integer> mb = new MathBox<>(new Integer[]{5, -20, 1, 22});
        System.out.println(mb.summator());
        List<Double> splitted = mb.splitter(2);
        System.out.println(splitted);
        System.out.println(1 / 2);
    }
}
