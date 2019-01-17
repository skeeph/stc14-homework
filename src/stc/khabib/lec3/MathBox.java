package stc.khabib.lec3;

import java.util.*;

public class MathBox<T extends Number> {
    private Set<T> store;

    public MathBox(T[] numbers) {
        this.store = new TreeSet<>(Arrays.asList(numbers));
    }


    public Double summator() {
        Double sum = 0d;
        for (T i : this.store) {
            sum = sum + i.doubleValue();
        }
        return sum;
    }

    public List<Double> splitter(T divider) {
        List<Double> result = new ArrayList<>();
        for (T i : this.store) {
            result.add(i.doubleValue() / divider.doubleValue());
        }
        return result;
    }

    public boolean remove(T element) {
        return this.store.remove(element);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathBox mathBox = (MathBox) o;
        return Objects.equals(store, mathBox.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(store);
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        for (T element : this.store) {
            content.append(element);
            content.append(", ");
        }
        return getClass().getSimpleName() + "<" + content + '>';
    }
}
