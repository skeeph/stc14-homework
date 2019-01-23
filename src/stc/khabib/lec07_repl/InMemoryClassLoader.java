package stc.khabib.lec07_repl;

import java.util.Map;

public class InMemoryClassLoader extends ClassLoader {
    private Map<String, byte[]> classBytes;

    public InMemoryClassLoader(Map<String, byte[]> classBytes) {
        this.classBytes = classBytes;
    }

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
