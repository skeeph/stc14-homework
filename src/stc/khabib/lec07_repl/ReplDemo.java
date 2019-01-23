package stc.khabib.lec07_repl;

public class ReplDemo {
    public static void main(String[] args) {
        Repl repl = new Repl();
        String code = repl.getCodeFromTerminal();
        String a = repl.readArgument("A");
        String b = repl.readArgument("B");
        repl.compileAndRun(code, a, b);
    }
}


