package khabib.lec06_serde;

import java.io.IOException;

/**
 * Класс оберка вокруг классов Serializer и Deserializer, реализующий интерфейс из условия задачи.
 */
public class Serialization {
    private final Serializer sz = new Serializer();
    private final Deserializer dsz = new Deserializer();

    /**
     * Сериализация
     *
     * @param object Объект для сериализации
     * @param file   файл для записи результатов
     * @throws IOException            ошибка записи в файл
     * @throws IllegalAccessException ошибка доступа к полям объекта
     */
    void serialize(Object object, String file) throws IOException, IllegalAccessException {
        this.sz.serialize(object, file);
    }

    /**
     * @param file файл с сериализованными данными
     * @return десериализованный объект
     * @throws Exception возникшая ошибка
     */
    Object deSerialize(String file) throws Exception {
        return this.dsz.deSerialize(file);
    }
}
