package stc.khabib.lec06_serde;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Serializer {
    private final Set<String> primitiveWrappers = new HashSet<>(Arrays.asList(
            "Byte", "Short", "Integer", "Long", "Float", "Double", "Character", "Boolean", "String"
    ));

    public void serialize(Object o, String path) throws IllegalAccessException {
        String name = o.getClass().getCanonicalName();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"className\": \"").append(name).append("\",");
        String objectJson = serializeObject(o);
        sb.append("\"fields\":").append(objectJson).append("}");
        System.out.println(sb.toString());
    }

    private String serializeObject(Object o) throws IllegalAccessException {
        if (o == null) {
            return "null";
        }
        Class type = o.getClass();
        if (type.isPrimitive() || primitiveWrappers.contains(type.getSimpleName())) {
            String res = o.toString();
            if (type.getSimpleName().equals("String")) {
                res = "\"" + res + "\"";
            }
            return res;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        String field;

        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            declaredField.setAccessible(true);
            field = serializeField(o, declaredField);
            sb.append(field);
            if (i < declaredFields.length - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private String serializeField(Object o, Field f) throws IllegalAccessException {
        StringBuilder fieldSb = new StringBuilder();
        Class fieldType = f.getType();
        fieldSb.append("\"").append(f.getName()).append("\":");

        if (fieldType.isPrimitive() || primitiveWrappers.contains(fieldType.getSimpleName())) {
            if (fieldType.getSimpleName().equals("String")) {
                fieldSb.append("\"").append(f.get(o)).append("\"");
            } else {
                fieldSb.append(f.get(o));
            }
        } else if (fieldType.isArray()) {
            String arrayValue = serializeArray(o, f);
            fieldSb.append(arrayValue);
        } else {
            String objectValue = serializeObject(f.get(o));
            fieldSb.append(objectValue);
        }
        return fieldSb.toString();
    }

    private String serializeArray(Object o, Field array) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Object arr = array.get(o);
        int length = Array.getLength(arr);
        for (int i = 0; i < length; i++) {
            Object arrayElement = Array.get(arr, i);
            sb.append(serializeObject(arrayElement));
            if (i < length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }


}
