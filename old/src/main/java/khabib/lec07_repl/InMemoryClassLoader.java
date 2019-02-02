package khabib.lec07_repl;

import java.util.Map;

/**
 * Загрузчик классов из памяти
 */
public class InMemoryClassLoader extends ClassLoader {
    private Map<String, byte[]> classBytes;

    /**
     * Конструктор.
     *
     * @param classBytes Map содержащий {имя класса}:{байт код}
     */
    public InMemoryClassLoader(Map<String, byte[]> classBytes) {
        this.classBytes = classBytes;
    }

    /**
     * Функция сначала ищет класс в памяти, если не найдено, выполняет стандартный поиск
     *
     * @param className имя класса
     * @return класс
     * @throws ClassNotFoundException класс не найден
     */
    protected Class findClass(String className) throws ClassNotFoundException {
        byte[] buf = classBytes.get(className);
        if (buf != null) {
            classBytes.put(className, null);
            return defineClass(className, buf, 0, buf.length);
        } else {
            return super.findClass(className);
        }
    }
}
