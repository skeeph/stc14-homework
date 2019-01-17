package stc.khabib.lec3;

import java.util.*;

public class MathBox extends ObjectBox {
    private Set<Integer> store;

    public MathBox(Integer[] numbers) {
        this.store = new TreeSet<>(Arrays.asList(numbers));
    }

    public Integer summator() {
        Integer sum = 0;
        for (Integer i : this.store) {
            sum += i;
        }
        return sum;
    }

    public List<Integer> splitter(Integer divider) {
        List<Integer> result = new ArrayList<>();
        for (Integer i : this.store) {
            result.add(i / divider);
        }
        return result;
    }

    public boolean remove(Integer element) {
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
        for (Object element : this.store) {
            content.append(element);
            content.append(", ");
        }
        return getClass().getSimpleName() + "<" + content + '>';
    }
}
