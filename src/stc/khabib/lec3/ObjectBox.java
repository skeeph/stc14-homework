package stc.khabib.lec3;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ObjectBox<T> {
    private Set<T> store;

    /**
     * Constructor, creates ObjectBox with empty Collection
     */
    public ObjectBox() {
        this.store = new HashSet<>();
    }

    /**
     * Создает коллекцию на основе массива.
     *
     * @param objects массив объектов
     */
    public ObjectBox(T[] objects) {
        this.store = new HashSet<>();
        Collections.addAll(this.store, objects);
    }

    /**
     * @return get internal collection
     */
    protected Set<T> getStore() {
        return store;
    }

    /**
     * Добавить элемент в коллекцию
     *
     * @param elem элемент для добавления
     */
    public boolean addObject(T elem) {
        return this.getStore().add(elem);
    }

    /**
     * Удалить элемент из коллекции, если он там есть
     *
     * @param elem Элемент для удаления
     * @return был ли найден и удален элемент
     */
    public boolean deleteObject(T elem) {
        return this.getStore().remove(elem);
    }

    /**
     * @return выводит содержимое коллекции в строку
     */
    public String dump() {
        StringBuilder content = new StringBuilder();
        for (Object element : this.getStore()) {
            content.append(element);
            content.append(", ");
        }
        return "<" + content.toString() + ">";
    }

    /**
     * @return Строковое представление объекта
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + dump();
    }

    /**
     * Проверка равенства двух объектов
     *
     * @param o второй объект для сравнения
     * @return равны ли два объекта
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectBox box = (ObjectBox) o;
        return Objects.equals(this.getStore(), box.getStore());
    }

    // TODO: hashcode не учитывает null

    /**
     * @return хеш-код объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getStore());
    }
}
