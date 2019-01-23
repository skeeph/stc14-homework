package stc.khabib.lec07_repl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class InMemoryClassLoader extends URLClassLoader {
    private Map<String, byte[]> classBytes;

    public InMemoryClassLoader(Map<String, byte[]> classBytes,
                               String classPath, ClassLoader parent) {
        super(toURLs(classPath), parent);
        this.classBytes = classBytes;
    }

    public InMemoryClassLoader(Map<String, byte[]> classBytes, String classPath) {
        this(classBytes, classPath, ClassLoader.getSystemClassLoader());
    }

    public InMemoryClassLoader(Map<String, byte[]> classBytes) {
        this(classBytes, null, ClassLoader.getSystemClassLoader());
    }

    private static URL[] toURLs(String classPath) {
        if (classPath == null) {
            return new URL[0];
        }

        List<URL> list = new LinkedList<>();
        StringTokenizer st = new StringTokenizer(classPath, File.pathSeparator);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            File file = new File(token);
            if (file.exists()) {
                try {
                    list.add(file.toURI().toURL());
                } catch (MalformedURLException ignored) {
                }
            } else {
                try {
                    list.add(new URL(token));
                } catch (MalformedURLException ignored) {
                }
            }
        }
        return list.toArray(new URL[0]);
    }

    public Class load(String className) throws ClassNotFoundException {
        return loadClass(className);
    }

    public Iterable<Class> loadAll() throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>(classBytes.size());
        for (String name : classBytes.keySet()) {
            classes.add(loadClass(name));
        }
        return classes;
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
