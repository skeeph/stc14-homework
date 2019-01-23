package stc.khabib.lec07_repl;

import javax.tools.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
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


    private Map<String, byte[]> compile(String fileName, String source,
                                        Writer err, String sourcePath, String classPath) throws IOException {
        InMemoryJavaFileManager fileManager = new InMemoryJavaFileManager(stdFileManager);
        List<JavaFileObject> compUnits = new ArrayList<>(1);
        compUnits.add(fileManager.makeStringSource(fileName, source));

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        // Опции Java компилятора
        List<String> options = new ArrayList<>();


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
                err, fileManager, diagnostics, options, null, compUnits
        );

        // Если произошла ошибка компиляции
        if (!task.call()) {
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                err.write(diagnostic.toString() + "\n");
            }
            err.flush();
            return null;
        }


        Map<String, byte[]> classBytes = fileManager.getClassBytes();
        fileManager.close();
        return classBytes;
    }

    public Map<String, byte[]> compile(String fileName, String source) throws IOException {
        return compile(fileName, source, new PrintWriter(System.err),
                System.getProperty("user.dir"),
                System.getProperty("java.class.path")
        );
    }

}
