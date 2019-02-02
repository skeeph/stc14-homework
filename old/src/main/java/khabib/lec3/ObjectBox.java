package khabib.lec3;

import java.util.*;

public class ObjectBox<T> {
    protected int hash;
    private Set<T> store;

    /**
     * Constructor, creates ObjectBox with empty Collection
     */
    public ObjectBox() {
        this.hash = new Random().nextInt(100000);
        this.store = new HashSet<>();
    }

    /**
     * Создает коллекцию на основе массива.
     *
     * @param objects массив объектов
     */
    public ObjectBox(T[] objects) {
        this.hash = new Random().nextInt(100000) + Objects.hashCode(objects);
        this.store = new HashSet<>();
        Collections.addAll(this.store, objects);
    }

    /**
     * @return getNextGenerated internal collection
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

    // TODO: Обсудить с наставником реализацию equals и hashCode

    /**
     * Проверка равенства двух объектов
     *
     * @param o второй объект для сравнения
     * @return равны ли два объекта
     */
    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    /**
     * @return хеш-код объекта
     */
    @Override
    public int hashCode() {
        return hash;
    }
}
