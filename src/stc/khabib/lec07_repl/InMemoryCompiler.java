package stc.khabib.lec07_repl;

import javax.tools.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryCompiler {
    private JavaCompiler compiler;
    private StandardJavaFileManager stdFileManager;

    public InMemoryCompiler() {
        this.compiler = ToolProvider.getSystemJavaCompiler();
        if (this.compiler == null) {
            throw new RuntimeException("Не найден компилятор в системе. JDK установлен?");
        }
        stdFileManager = this.compiler.getStandardFileManager(null, null, null);
    }

    private Map<String, byte[]> compile(final List<JavaFileObject> units,
                                        final InMemoryJavaFileManager fileManager,
                                        Writer err, String sourcePath, String classPath)
            throws IOException {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();


        // Опции Java компилятора
        List<String> options = new ArrayList<String>();


        if (sourcePath != null) {
            options.add("-sourcepath");
            options.add(sourcePath);
        }

        if (classPath != null) {
            options.add("-classpath");
            options.add(classPath);
        }


        //Компиляция
        JavaCompiler.CompilationTask task = compiler.getTask(
                err, fileManager, diagnostics, options, null, units
        );

        // Если произошла ошибка компиляции
        if (!task.call()) {
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                err.write(diagnostic.toString() + "\n");
            }
            return null;
        }


        Map<String, byte[]> classBytes = fileManager.getClassBytes();
        fileManager.close();
        return classBytes;

    }


    private Map<String, byte[]> compile(String fileName, String source,
                                        Writer err, String sourcePath, String classPath) throws IOException {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        InMemoryJavaFileManager fileManager = new InMemoryJavaFileManager(stdFileManager);
        List<JavaFileObject> compUnits = new ArrayList<JavaFileObject>(1);
        compUnits.add(fileManager.makeStringSource(fileName, source));
        return compile(compUnits, fileManager, err, sourcePath, classPath);
    }

    public Map<String, byte[]> compile(String fileName, String source) throws IOException {
        return compile(fileName, source, new PrintWriter(System.err), null, null);
    }

    public Method compileStaticMethod(String methodName, String className, String source) throws IOException, ClassNotFoundException {
        Map<String, byte[]> classBytes = compile(className + ".java", source);
        InMemoryClassLoader classLoader = new InMemoryClassLoader(classBytes);
        Class clazz = classLoader.loadClass(className);
        final Method[] methods = clazz.getDeclaredMethods();
        for (final Method method : methods) {
            if (method.getName().equals(methodName)) {
                if (!method.isAccessible()) method.setAccessible(true);
                return method;
            }
        }
        throw new NoSuchMethodError(methodName);
    }

}
