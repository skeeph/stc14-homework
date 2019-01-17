package stc.khabib.lec3;

import java.util.*;

/**
 * Класс для хранения чисел
 *
 * @param <T> Тип хранимых чисел
 */
public class MathBox<T extends Number> extends ObjectBox {
    private Set<T> store;

    @Override
    protected Set getStore() {
        return store;
    }

    public MathBox(T[] numbers) {
        this.store = new TreeSet<>(Arrays.asList(numbers));
    }


    public T summator() {
        Double sum = 0d;
        for (T i : this.store) {
            sum = sum + i.doubleValue();
        }
        return (T) sum;
    }

    public List<Double> splitter(T divider) {
        List<Double> result = new ArrayList<>();
        for (T i : this.store) {
            result.add(i.doubleValue() / divider.doubleValue());
        }
        return result;
    }

    public boolean remove(T element) {
        return this.deleteObject(element);
    }

}
