package stc.khabib.lec06_serde;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс - десериализатор
 */
public class Deserializer {

    public Deserializer() {
    }


    /**
     * Функци читает json из файла, и десериалузет из него объект
     *
     * @param path Путь к json - файлу
     * @return Десериализованный объект
     * @throws InvalidJsonException   вызывается ошибками в формате json- файла
     * @throws IOException            ошибка чтения файла
     * @throws ClassNotFoundException не найден класс, объект которого сериализован в файле
     */
    public Object deSerialize(String path) throws InvalidJsonException, IOException, ClassNotFoundException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        String content;

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            content = reader.lines().collect(Collectors.joining("\n"));
        }

        Iterator<Map.Entry<String, String>> it = tokenize(content);
        Map<String, String> json = new HashMap<>();

        for (; it.hasNext(); ) {
            Map.Entry<String, String> e = it.next();
            json.put(e.getKey(), e.getValue());
        }

        String className = json.get("className");
        if (className == null) {
            throw new InvalidJsonException("No `className` field found in json");
        }

        String fields = json.get("fields");
        if (fields == null) {
            throw new InvalidJsonException("Empty `fields` in serialized");
        }
        return createObject(className, fields);
    }

    /**
     * Функция создает объект класса className, и десериализует его поля из json строки fields
     *
     * @param className имя класса для создания объекта
     * @param fields    строка с сериализованными полями объекта
     * @return новый объект
     * @throws ClassNotFoundException не найден класс, объект которого сериализован в файле
     * @throws InstantiationException произошла ошибка создания объекта
     * @throws IllegalAccessException ошибка доступа к полям объекта
     * @throws NoSuchFieldException   попытка доступа к несуществующему полю класса
     * @throws InvalidJsonException   ошибка в формате сериализованных данных
     */
    private Object createObject(String className, String fields) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, InvalidJsonException {
        Object o = Serializer.class.getClassLoader().loadClass(className).newInstance();
        Iterator<Map.Entry<String, String>> it = tokenize(fields);
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();

            if (next.getValue().trim().substring(0, 1).equals("{")) {
                setObject(o, next);
            } else if (next.getValue().trim().substring(0, 1).equals("[")) {
                setArray(o, next);
            } else {
                setValue(o, next);
            }
        }
        return o;
    }

    /**
     * Десериализация массива и присвавание соответствующему полю объекта
     *
     * @param o  объект
     * @param kv пара {имя поля}:{сериализованный метод}
     * @throws NoSuchFieldException   попытка доступа к несуществующему полю
     * @throws IllegalAccessException ошибка доступа к полю
     */
    private void setArray(Object o, Map.Entry<String, String> kv) throws NoSuchFieldException, IllegalAccessException {
        Field f = o.getClass().getDeclaredField(kv.getKey());
        f.setAccessible(true);
        String arrStr = kv.getValue();
        String[] a = arrStr.substring(1, arrStr.length() - 2).split(", ");
        Object array = Array.newInstance(f.getType().getComponentType(), a.length);
        for (int i = 0; i < a.length; i++) {
            Array.set(array, i, a[i].substring(1, a[i].length() - 1));
        }
        f.set(o, array);
    }

    /**
     * Десериализация вложенных объектов, и присваивание их соответствующему полю
     *
     * @param o  объект
     * @param kv пара {имя поля}:{сериализованный json-объект}
     * @throws NoSuchFieldException   попытка доступа к несуществующему полю
     * @throws ClassNotFoundException не найден класс, экземпляром которого является поле-объект
     * @throws IllegalAccessException ошибка доступа к полю
     * @throws InstantiationException ошибка создания объекта
     * @throws InvalidJsonException   неверный формат сериализованных данных
     */
    private void setObject(Object o, Map.Entry<String, String> kv) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvalidJsonException {
        Field f = o.getClass().getDeclaredField(kv.getKey());
        f.setAccessible(true);
        Object nested = createObject(f.getType().getCanonicalName(), kv.getValue());
        f.set(o, nested);
    }

    /**
     * Установка значений полю объекта
     *
     * @param o  объект
     * @param kv пара {имя поля}:{значение поля}
     * @throws NoSuchFieldException   поле не найдено
     * @throws IllegalAccessException ошибка доступа к полю
     */
    private void setValue(Object o, Map.Entry<String, String> kv) throws NoSuchFieldException, IllegalAccessException {
        Field f = o.getClass().getDeclaredField(kv.getKey());
        f.setAccessible(true);
        if (f.getType().isPrimitive()) {
            Object val = convertTo(kv.getValue(), f.getType());
            f.set(o, val);
        } else if (kv.getValue().trim().equals("null")) {
            f.set(o, null);
        } else {
            f.set(o, kv.getValue());
        }
    }

    /**
     * Парсит значени поля из строки, в тип поля, которому его нужно присвоить
     *
     * @param value строка с значением
     * @param type  тип в который нужно превратить эту строку
     * @return значение в необходимом типе
     */
    private Object convertTo(String value, Class type) {
        value = value.trim();
        if (type == long.class || type == Long.class)
            return Long.valueOf(value);
        if (type == int.class || type == Integer.class)
            return Integer.valueOf(value);
        if (type == boolean.class || type == Boolean.class)
            return Boolean.valueOf(value);
        if (type == double.class || type == Double.class)
            return Double.valueOf(value);
        if (type == byte.class || type == Byte.class)
            return Byte.valueOf(value);
        if (type == short.class || type == Short.class)
            return Short.valueOf(value);
        if (type == float.class || type == Float.class)
            return Float.valueOf(value);
        if (type == char.class || type == Character.class)
            return value.charAt(0);

        return value;
    }

    /**
     * Функция парсит json строку и возвращает итератор по парам ключ:значение верхнего уровня.
     * Вложенные массивы и объекты возвращаются в виде json строки
     *
     * @param content строка с сериализованным объектом
     * @return итератор по парам ключ:значение верхнего уровня.
     * @throws InvalidJsonException ошибка в сериализованных данных
     */
    private Iterator<Map.Entry<String, String>> tokenize(String content) throws InvalidJsonException {
        return JSONTokenizer.tokenize(content).entrySet().iterator();
    }

}
