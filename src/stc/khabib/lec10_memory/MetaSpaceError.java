package stc.khabib.lec10_memory;

import stc.khabib.lec07_repl.InMemoryClassLoader;
import stc.khabib.lec07_repl.InMemoryCompiler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class MetaSpaceError {
    private static final int LOOP_COUNT = 100_000_000;
    private static final String CLASS_TEMPLATE = "public class %s {\n" +
            "public void doWork(String a, String b) {\n  \n}}";

    public static void main(String[] args) {
        InMemoryCompiler compiler = new InMemoryCompiler();
        List<Object> objects = new LinkedList<>();
        for (int i = 0; i < LOOP_COUNT; i++) {
            String className = "Class" + i;
            String fileName = className + ".java";

            String source = String.format(CLASS_TEMPLATE, className);
            Map<String, byte[]> classBytes;
            try {
                classBytes = compiler.compile(fileName, source);
            } catch (IOException e) {
                System.err.println("Ошибка компиляции: " + e);
                return;
            }

            InMemoryClassLoader classLoader = new InMemoryClassLoader(classBytes);
            Class clazz;
            try {
                clazz = classLoader.loadClass(className);
            } catch (ClassNotFoundException e) {
                System.err.println("Ошибка загрузки класса: " + e);
                return;
            }

            Object o;
            try {
                o = clazz.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                System.err.println("Ошибка создания объекта: " + e);
                return;
            }
            objects.add(o);
        }
        System.out.println(objects.get(50));
        System.out.println(objects.size());
    }
}
