package stc.khabib.lec06_serde;

import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Deserializer {
    private static String TRAILINIG_REMOVER = "(^\\s*\")|([\"\\s,]*$)";

    public Deserializer() {
    }


    private String readArray(Iterator<String> it, String begin) {
        StringBuilder sb = new StringBuilder(begin).append(", ");
        int arrayCount = 1;
        while (it.hasNext()) {
            String token = it.next();
            sb.append(token).append(", ");
            if (token.contains("[")) {
                arrayCount++;
            } else if (token.contains("]")) {
                arrayCount--;
                if (arrayCount == 0) {
                    break;
                }
            }
        }
        return sb.toString();
    }


    private String readObject(Iterator<String> it, String begin) {
        StringBuilder sb = new StringBuilder(begin).append(", ");
        int nestedCount = 1;
        while (it.hasNext()) {
            String token = it.next().trim();
            if (token.equals("")) {
                continue;
            }
            if (token.charAt(token.length() - 1) == ',') {
                token = token.substring(0, token.length() - 1);
            }
            sb.append(token).append(", ");
            if (token.contains("{")) {
                nestedCount++;
            } else if (token.contains("}")) {
                nestedCount--;
                if (nestedCount == 0) {
                    break;
                }
            }
        }
        return sb.toString();
    }

    public Object deSerialize(String path) throws Exception {
        String content;

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            content = reader.lines().collect(Collectors.joining("\n"));
        }

        Iterator<Map.Entry<String, String>> it = tokenize(content);
        Map<String, String> json = new HashMap<>();

        for (; it.hasNext(); ) {
            Map.Entry<String, String> e = it.next();
            json.put(e.getKey(), e.getValue());
        }

        String className = json.get("className");
        if (className == null) {
            throw new SerializationException("No `className` field found in json");
        }

        String fields = json.get("fields");
        if (fields == null) {
            throw new SerializationException("Empty `fields` in serialized");
        }
        return createObject(className, fields);
    }

    private Object createObject(String className, String fields) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Object o = Serializer.class.getClassLoader().loadClass(className).newInstance();
        Iterator<Map.Entry<String, String>> it = tokenize(fields);
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();

            if (next.getValue().trim().substring(0, 1).equals("{")) {
                setObject(o, next);
            } else if (next.getValue().trim().substring(0, 1).equals("[")) {
                setArray(o, next);
            } else {
                setValue(o, next);
            }
        }
        return o;
    }

    private void setArray(Object o, Map.Entry<String, String> kv) throws NoSuchFieldException, IllegalAccessException {
        Field f = o.getClass().getDeclaredField(kv.getKey());
        f.setAccessible(true);
        String arrStr = kv.getValue();
        String[] a = arrStr.substring(1, arrStr.length() - 2).split(", ");
        Object array = Array.newInstance(f.getType().getComponentType(), a.length);
        for (int i = 0; i < a.length; i++) {
            Array.set(array, i, a[i].substring(1, a[i].length() - 1));
        }
        f.set(o, array);
    }

    private void setObject(Object o, Map.Entry<String, String> kv) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Field f = o.getClass().getDeclaredField(kv.getKey());
        f.setAccessible(true);
        Object nested = createObject(f.getType().getCanonicalName(), kv.getValue());
        f.set(o, nested);
    }

    private void setValue(Object o, Map.Entry<String, String> kv) throws NoSuchFieldException, IllegalAccessException {
        Field f = o.getClass().getDeclaredField(kv.getKey());
        f.setAccessible(true);
        if (f.getType().isPrimitive()) {
            Object val = convertTo(kv.getValue(), f.getType());
            f.set(o, val);
        } else if (kv.getValue().trim().equals("null")) {
            f.set(o, null);
        } else {
            f.set(o, kv.getValue());
        }
    }

    private Map.Entry<String, String> parseToken(String token) {
        String[] kv = new String[2];
        int index = token.indexOf(":");
        kv[0] = token.substring(0, index);
        kv[1] = token.substring(index + 1);
        kv = Arrays.stream(kv)
                .map(x -> x.replaceAll(TRAILINIG_REMOVER, ""))
                .toArray(String[]::new);
        return new AbstractMap.SimpleEntry<>(kv[0], kv[1]);
    }

    private Object convertTo(String value, Class type) {
        value = value.trim();
        if (type == long.class || type == Long.class)
            return Long.valueOf(value);
        if (type == int.class || type == Integer.class)
            return Integer.valueOf(value);
        if (type == boolean.class || type == Boolean.class)
            return Boolean.valueOf(value);
        if (type == double.class || type == Double.class)
            return Double.valueOf(value);
        if (type == byte.class || type == Byte.class)
            return Byte.valueOf(value);
        if (type == short.class || type == Short.class)
            return Short.valueOf(value);
        if (type == float.class || type == Float.class)
            return Float.valueOf(value);
        if (type == char.class || type == Character.class)
            return value.charAt(0);

        return value;
    }

    private Iterator<Map.Entry<String, String>> tokenize(String content) {
        content = content.trim();
        content = content.substring(1, content.length() - 1);
        List<String> tokens = new LinkedList<>(Arrays.asList(content.split(",\\s")));
        List<Map.Entry<String, String>> result = new LinkedList<>();

        Iterator<String> it = tokens.iterator();
        while (it.hasNext()) {
            String token = it.next().trim();
            String res;
            if (token.contains("{")) {
                res = readObject(it, token);
            } else if (token.contains("[")) {
                res = readArray(it, token);
            } else {
                res = token;
            }
            result.add(parseToken(res.trim() + ", "));
        }
        return result.iterator();
    }

}
