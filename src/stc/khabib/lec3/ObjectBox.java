package stc.khabib.lec3;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ObjectBox<T> {
    private Set<T> store;

    public ObjectBox() {
        this.store = new HashSet<>();
    }

    protected Set getStore() {
        return store;
    }

    public void addObject(T elem) {
        this.store.add(elem);
    }

    public boolean deleteObject(Object elem) {
        return this.getStore().remove(elem);
    }

    public String dump() {
        StringBuilder content = new StringBuilder();
        for (Object element : this.getStore()) {
            content.append(element);
            content.append(", ");
        }
        return "<" + content.toString() + ">";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + dump();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectBox box = (ObjectBox) o;
        return Objects.equals(store, box.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(store);
    }
}
