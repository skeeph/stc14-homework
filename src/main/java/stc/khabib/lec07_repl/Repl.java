package stc.khabib.lec07_repl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс в котором реализованно чтение кода из консоли, его компиляция и выполнения
 */
public class Repl {
    private static final String CLASS_NAME = "SomeClass";
    private static final String FILE_NAME = CLASS_NAME + ".java";
    /**
     * Шаблон класса, в который подставляется тело метода, прочитанное из консоли
     */
    private static String CLASS_TEMPLATE = "public class SomeClass implements stc.khabib.lec07_repl.Worker{\n" +
            "public void doWork(String a, String b) {\n %s \n}}";
    private Scanner scan;
    private InMemoryCompiler compiler;

    /**
     * Конструктор, инициализирует компилятор
     */
    public Repl() {
        compiler = new InMemoryCompiler();
        scan = new Scanner(System.in);
    }

    /**
     * Чтение кода из консоли
     *
     * @return строка с прочитанным кодом
     */
    public String getCodeFromTerminal() {
        System.out.println("Ввведите тело метода. Инструкции требующие импортов сторонних библиотек запрещены.");
        System.out.println("Два перехода на новую строку означают конец метода.");

        int newLineCount = 0;
        StringBuilder code = new StringBuilder();

        String line;
        while (newLineCount < 2) {
            line = scan.nextLine();
            if (newLineCount > 0) {
                if (line.equals("")) {
                    break;
                }
                newLineCount = 0;
            }
            if (line.equals("")) {
                newLineCount++;
                continue;
            }
            code.append(line).append(" ");
        }
        return code.toString();
    }

    /**
     * Компиляция и запуск кода
     *
     * @param methodCode код тела метода doWork
     * @param args       аргументы метода
     */
    public void compileAndRun(String methodCode, String... args) {
        String source = String.format(CLASS_TEMPLATE, methodCode);
        Map<String, byte[]> classBytes;
        try {
            classBytes = compiler.compile(FILE_NAME, source);
        } catch (IOException e) {
            System.err.println("Ошибка компиляции: " + e);
            return;
        }

        InMemoryClassLoader classLoader = new InMemoryClassLoader(classBytes);
        Class clazz;
        try {
            clazz = classLoader.loadClass(CLASS_NAME);
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

        try {
            System.out.println("Вызов метода через рефлексию");
            Method meth = clazz.getDeclaredMethod("doWork", String.class, String.class);
            meth.invoke(o, args[0], args[1]);
        } catch (Exception e) {
            System.err.println("Ошибка выполнения кода через Reflection: " + e);
            return;
        }

        try {
            System.out.println("Вызов метода через приведение к интерфейсу");
            Worker w = (Worker) o;
            w.doWork(args[0], args[1]);
        } catch (Exception e) {
            System.err.println("Ошибка выполнения кода через приведение к интерфейсу " + e);
        }
    }

    /**
     * Чтение значений аргументов из консоли
     *
     * @param argName имя аргумента
     * @return значение
     */
    public String readArgument(String argName) {
        System.out.println("Введите значение аргумента(только строку)");
        System.out.print(argName + "=");
        return scan.nextLine();
    }
}
