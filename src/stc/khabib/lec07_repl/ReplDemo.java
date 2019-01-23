package stc.khabib.lec07_repl;

import java.lang.reflect.Method;

public class ReplDemo {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        InMemoryCompiler compiler = new InMemoryCompiler();
        String source = "public final class Solution {\n"
                + "public static String greeting(String name) {\n"
                + "\treturn \"Hello \" + name;\n" + "}\n}\n";
        Method greeting = compiler.compileStaticMethod("greeting", "Solution", source);
        System.out.println(greeting.invoke(null, "lorem"));
    }
}


