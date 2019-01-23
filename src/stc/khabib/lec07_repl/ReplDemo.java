package stc.khabib.lec07_repl;

import java.lang.reflect.Method;
import java.util.Map;

public class ReplDemo {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        InMemoryCompiler compiler = new InMemoryCompiler();
        String source = "public class Solution {\n"
                + "public void doWork(String a, String b) {\n"
                + "System.out.println(\"This is interface sample\");\n" +
                "        System.out.println(a);\n" +
                "        System.out.println(b);" +
                "}\n}\n";

        String className = "Solution";
        Map<String, byte[]> classBytes = compiler.compile(className + ".java", source);
        InMemoryClassLoader classLoader = new InMemoryClassLoader(classBytes);
        Class clazz = classLoader.loadClass(className);

        Object o = clazz.newInstance();
        Method meth = clazz.getDeclaredMethod("doWork", String.class, String.class);
        meth.invoke(o, "a", "b");
//        Worker w = (Worker)o;
//        w.doWork("Ты", "Труп");
    }
}


