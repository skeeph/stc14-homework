package stc.khabib.lec3;

import java.util.*;

/**
 * Класс для хранения чисел
 *
 * @param <T> Тип хранимых чисел
 */
@SuppressWarnings("unused")
public class MathBox<T extends Number> extends ObjectBox {
    private Set<T> store;

    /**
     * Конструктор создает MathBox, поместив в коллекцию переданный
     * в аргументе массив чисел.
     *
     * @param numbers массив чисел для создания объекта
     */
    public MathBox(T[] numbers) {
        checkInput(numbers);
        this.hash = new Random().nextInt(100000) + Objects.hashCode(numbers);
        this.store = new TreeSet<>(Arrays.asList(numbers));
    }

    private void checkInput(T[] input) {
        for (T t : input) {
            if (t == null) {
                throw new IllegalArgumentException("Null values aren't allowed");
            }
        }
    }

    /**
     * @return внутренная коллекция для хранения чисел
     */
    @Override
    protected Set<T> getStore() {
        return store;
    }

    /**
     * Суммирует все элементы коллекции
     *
     * @return Сумма элементов коллекции
     */
    public Double summator() {
        double sum = 0d;
        for (T i : this.getStore()) {
            sum = sum + i.doubleValue();
        }
        return sum;
    }

    /**
     * Удаление элемента из коллекции. Если аргумент равен null или он другого типа чем Number
     * бросает ошибку IllegalArgumentException, в ином случае вызывает процедуру удаления из ObjectBox
     *
     * @param elem элемент для удаления
     * @return был ли удален элемент
     */
    @Override
    public boolean deleteObject(Object elem) {
        if (!(elem instanceof Number)) {
            throw new IllegalArgumentException("elem must be not null Number");
        }
        return super.deleteObject(elem);
    }


    /**
     * Добавлени элемента в коллекции. Если аргумент равен равен null или он другого типа чем Number
     * бросает ошибку IllegalArgumentException в ином случае вызывает процедуру удаления из ObjectBox
     *
     * @param elem элемент для добавления
     */
    @Override
    public boolean addObject(Object elem) {
        if (!(elem instanceof Number)) {
            throw new IllegalArgumentException("elem must be not null Number");
        }
        return super.addObject(elem);
    }

    /**
     * Возвращает коллекцию, созданную из внутренный коллекции
     * делением каждого элемента на аргумет
     *
     * @param divider число на которое необходимо делить каждый элемент коллекции
     * @return новая коллекция
     */
    public List<Double> splitter(T divider) {
        List<Double> result = new ArrayList<>();
        for (T i : this.store) {
            result.add(i.doubleValue() / divider.doubleValue());
        }
        return result;
    }
}
