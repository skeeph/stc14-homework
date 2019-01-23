package stc.khabib.lec06_serde;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.regex.Pattern;

public class JSONTokenizer {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[+-]?([0-9]*[.])?[0-9]+");

    private JSONTokenizer() {
    }

    public static Map<String, String> tokenize(String jsonStr) throws SerializationException {

        jsonStr = jsonStr.trim();
        jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
        CharacterIterator it = new StringCharacterIterator(jsonStr);
        Map<String, String> tokens = new HashMap<>();
        while (it.current() != CharacterIterator.DONE) {
            skipWhitespace(it);
            if (it.current() == ',') {
                it.next();
                continue;
            }
            if (it.current() == CharacterIterator.DONE) break;
            String key = readKey(it);
            String value = readValue(it);
            tokens.put(key, value);
        }
        return tokens;
    }

    private static void skipWhitespace(CharacterIterator it) {
        while (Character.isWhitespace(it.current())) {
            it.next();
        }
    }

    private static String readValue(CharacterIterator it) throws SerializationException {
        skipWhitespace(it);
        char ch = it.current();
        skipWhitespace(it);
        if (Character.isDigit(ch) || ch == '-') {
            return readNumber(it);
        }
        switch (ch) {
            case '{':
                return readObject(it);
            case '[':
                return readArray(it);
            case '"':
                return readString(it);
            case 't':
            case 'f':
                return readBool(it);
            case 'n':
                return readNull(it);
            default:
                throw new SerializationException("Invalid json format");
        }
    }

    @SuppressWarnings("Duplicates")
    private static String readNumber(CharacterIterator it) throws SerializationException {
        StringBuilder sb = new StringBuilder();
        Set<Character> nullChars = new HashSet<>(Arrays.asList('-', '.', '+'));
        while (nullChars.contains(it.current()) || Character.isDigit(it.current())) {
            sb.append(it.current());
            it.next();
        }
        String b = sb.toString();
        if (!NUMBER_PATTERN.matcher(b).matches()) {
            throw new SerializationException("bad format number: " + b);
        }
        return b;
    }

    @SuppressWarnings("Duplicates")
    private static String readNull(CharacterIterator it) throws SerializationException {
        StringBuilder sb = new StringBuilder();
        Set<Character> nullChars = new HashSet<>(Arrays.asList('n', 'u', 'l'));
        while (nullChars.contains(it.current())) {
            sb.append(it.current());
            it.next();
        }
        String b = sb.toString();
        if (!b.equals("null")) {
            throw new SerializationException("invalid value: " + b);
        }
        return b;
    }

    private static String readBool(CharacterIterator it) throws SerializationException {
        StringBuilder sb = new StringBuilder();
        Set<Character> booleanChar = new HashSet<>(Arrays.asList('t', 'r', 'u', 'e', 'f', 'a', 'l', 's'));
        while (booleanChar.contains(it.current())) {
            sb.append(it.current());
            it.next();
        }
        String b = sb.toString();
        if (!(b.equals("true") || b.equals("false"))) {
            throw new SerializationException("invalid boolean value: " + b);
        }
        return b;
    }

    private static String readString(CharacterIterator it) {
        StringBuilder sb = new StringBuilder();
        it.next();
        boolean escaped = false;
        while (true) {
            char c = it.current();
            if (c == '\\') {
                escaped = true;
            }
            if (c == '"' && !escaped) {
                break;
            }
            sb.append(c);
            if (escaped) {
                escaped = false;
            }
            it.next();

        }
        it.next();
        return sb.toString();
    }

    private static String readKey(CharacterIterator it) {
        StringBuilder sb = new StringBuilder();
        while (it.current() != ':') {
            if (Character.isWhitespace(it.current()) || it.current() == '"') {
                it.next();
                continue;
            }
            sb.append(it.current());
            it.next();
        }
        it.next();
        return sb.toString();
    }

    private static String readObject(CharacterIterator it) {
        return readCompound(it, '{', '}');
    }

    private static String readArray(CharacterIterator it) {
        return readCompound(it, '[', ']');
    }

    private static String readCompound(CharacterIterator it, char openBrace, char closeBrace) {
        int braceCount = 0;
        StringBuilder sb = new StringBuilder();
        while (it.current() != CharacterIterator.DONE) {
            sb.append(it.current());
            if (it.current() == openBrace) {
                braceCount++;
            }
            if (it.current() == closeBrace) {
                braceCount--;
                if (braceCount == 0) {
                    it.next();
                    break;
                }
            }
            it.next();
        }
        return sb.toString();
    }

    public static void main(String[] args) throws SerializationException {
        Map<String, String> tokens = JSONTokenizer.tokenize("{\"hello\":3423}");
        System.out.println(tokens);
    }
}
