package stc.khabib.lec3;

import java.util.HashSet;
import java.util.Set;

public class ObjectBox {
    private Set store;

    public ObjectBox() {
        this.store = new HashSet();
    }

    public void addObject(Object elem) {
        this.store.add(elem);
    }

    public boolean deleteObject(Object elem) {
        return this.store.remove(elem);
    }

    public String dump() {
        StringBuilder content = new StringBuilder();
        for (Object element : this.store) {
            content.append(element);
            content.append(", ");
        }
        return "<" + content.toString() + ">";
    }
}
