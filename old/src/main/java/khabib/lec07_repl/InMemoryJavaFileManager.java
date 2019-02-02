package khabib.lec07_repl;

import javax.tools.*;
import java.io.*;
import java.net.URI;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Кастомный файл-менеджер для записи результатов компиляции в память
 */
public class InMemoryJavaFileManager extends ForwardingJavaFileManager {
    private static String EXT = ".java";

    // Место записи скомпилированных файлов
    private Map<String, byte[]> classBytes;

    protected InMemoryJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
        classBytes = new HashMap<>();
    }

    /**
     * Превращение имени файла в URI
     *
     * @param name имя файла
     * @return URI
     */
    static URI toURI(String name) {
        File file = new File(name);
        if (file.exists()) {
            return file.toURI();
        } else {
            try {
                final StringBuilder newUri = new StringBuilder();
                newUri.append("mfm:///");
                newUri.append(name.replace('.', '/'));
                if (name.endsWith(EXT))
                    newUri.replace(newUri.length() - EXT.length(), newUri.length(), EXT);
                return URI.create(newUri.toString());
            } catch (Exception exp) {
                return URI.create("mfm:///com/sun/script/java/java_source");
            }
        }
    }

    /**
     * @return классы, байт код которых содержится в памяти
     */
    public Map<String, byte[]> getClassBytes() {
        return classBytes;
    }

    /**
     * Не нужно ничего делать, т.к. храним все в памяти
     */
    @Override
    public void flush() {
    }

    /**
     * Создает @JavaFileObject из имени и содержимого файла
     * без записи на диск;
     *
     * @param fileName Имя файла
     * @param source   код файла
     * @return файл языка java
     */
    public JavaFileObject makeStringSource(String fileName, String source) {
        return new StringInputBuffer(fileName, source);
    }

    /**
     * Функция переопределяет получение выходного файла
     * и для класса возвращает его из памяти
     *
     * @param className имя класса
     * @param kind      тип
     * @return Объект с байт кодом
     * @throws IOException ошибка чтения файла
     */
    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location,
                                               String className,
                                               JavaFileObject.Kind kind,
                                               FileObject sibling) throws IOException {
        if (kind == JavaFileObject.Kind.CLASS) {
            return new ClassOutputBuffer(className);
        } else {
            return super.getJavaFileForOutput(location, className, kind, sibling);
        }
    }


    /**
     * Класс, представляет собой "виртуальный файл", в котором хранится код
     * программы
     */
    private static class StringInputBuffer extends SimpleJavaFileObject {
        final String code;

        /**
         * Создает "виртуальный" файл с именем @fileName и содержимым @code
         *
         * @param fileName имя файла
         * @param code     код файла
         */
        public StringInputBuffer(String fileName, String code) {
            super(toURI(fileName), Kind.SOURCE);
            this.code = code;
        }

        /**
         * Возвращает содержимое файла
         *
         * @param ignoreEncodingErrors игнорировать ошибки кодировик
         * @return содержимое файла
         */
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return CharBuffer.wrap(code);
        }
    }

    /**
     * Класс, представляет собой "виртуальный файл" с скомпилированным байт-кодом,
     * который хранится в памяти.
     */
    private class ClassOutputBuffer extends SimpleJavaFileObject {
        private String name;

        /**
         * Создать файл с заданным именем
         *
         * @param name Имя файла
         */
        protected ClassOutputBuffer(String name) {
            super(toURI(name), Kind.CLASS);
            this.name = name;
        }

        /**
         * Открыть поток для чтения
         *
         * @return поток для чтения
         */
        @Override
        public OutputStream openOutputStream() {
            return new FilterOutputStream(new ByteArrayOutputStream()) {
                @Override
                public void close() {
                    ByteArrayOutputStream bos = (ByteArrayOutputStream) out;
                    classBytes.put(name, bos.toByteArray());
                }
            };
        }
    }

}
