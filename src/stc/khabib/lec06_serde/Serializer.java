package stc.khabib.lec06_serde;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Serializer {
    private static final String IDENT = "   ";
    private final Set<String> primitiveWrappers = new HashSet<>(Arrays.asList(
            "Byte", "Short", "Integer", "Long", "Float", "Double", "Character", "Boolean", "String"
    ));

    public void serialize(Object o, String path) throws IllegalAccessException, IOException {
        String name = o.getClass().getCanonicalName();
        StringBuilder sb = new StringBuilder();
        sb.append("{\n").append(IDENT).append("\"className\": \"").append(name).append("\",");
        String objectJson = serializeObject(o, 2);
        sb.append("\n").append(IDENT).append("\"fields\":").append(objectJson).append("\n}");
        System.out.println(sb.toString());

        try (FileOutputStream fs = new FileOutputStream(path)) {
            fs.write(sb.toString().getBytes());
        }
    }

    private String serializePrimitive(Object o) {
        String res = o.toString();
        if (o instanceof String) {
            res = "\"" + res + "\"";
        }
        return res;
    }

    private String serializeObject(Object o, int identLevel) throws IllegalAccessException {
        if (o == null) {
            return "null";
        }
        Class type = o.getClass();
        if (type.isPrimitive() || primitiveWrappers.contains(type.getSimpleName())) {
            return this.serializePrimitive(o);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field f = declaredFields[i];
            f.setAccessible(true);

            StringBuilder fieldSb = new StringBuilder();
            Class fieldType = f.getType();
            fieldSb.append(getIdent(identLevel)).append("\"").append(f.getName()).append("\": ");

            if (fieldType.isArray()) {
                String arrayValue = serializeArray(o, f);
                fieldSb.append(arrayValue);
            } else {
                String objectValue = serializeObject(f.get(o), identLevel + 1);
                fieldSb.append(objectValue);
            }

            sb.append(fieldSb.toString());
            if (i < declaredFields.length - 1) {
                sb.append(",\n");
            }
        }
        sb.append("\n}");
        return sb.toString();
    }

    private String serializeArray(Object o, Field array) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Object arr = array.get(o);
        int length = Array.getLength(arr);
        for (int i = 0; i < length; i++) {
            Object arrayElement = Array.get(arr, i);
            sb.append(serializeObject(arrayElement, 1));
            if (i < length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private String getIdent(int identLevel) {
        return String.join("", Collections.nCopies(identLevel, IDENT));
    }

}


class SerializationException extends Exception {

    public SerializationException(String s) {
        super(s);
    }
}