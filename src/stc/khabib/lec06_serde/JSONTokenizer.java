package stc.khabib.lec06_serde;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Парсер JSON
 */
public class JSONTokenizer {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[+-]?([0-9]*[.])?[0-9]+");

    /**
     * Функция парсит переданную ей строку json, и возвращает Map,
     * с ключами и значениями соответствующими верхнему уровню в json'е.
     * Вложенные массивы и объекты возвращаются в виде строки
     *
     * @param jsonStr json
     * @return ключи и значения верхнего уровня в json
     * @throws InvalidJsonException невалидный json
     */
    public static Map<String, String> tokenize(String jsonStr) throws InvalidJsonException {

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

    /**
     * Пропускает незначимые пробелы.
     *
     * @param it итератор по json строке
     */
    private static void skipWhitespace(CharacterIterator it) {
        while (Character.isWhitespace(it.current())) {
            it.next();
        }
    }

    private static String readValue(CharacterIterator it) throws InvalidJsonException {
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
                throw new InvalidJsonException("Invalid json format");
        }
    }

    @SuppressWarnings("Duplicates")
    private static String readNumber(CharacterIterator it) throws InvalidJsonException {
        StringBuilder sb = new StringBuilder();
        Set<Character> nullChars = new HashSet<>(Arrays.asList('-', '.', '+'));
        while (nullChars.contains(it.current()) || Character.isDigit(it.current())) {
            sb.append(it.current());
            it.next();
        }
        String b = sb.toString();
        if (!NUMBER_PATTERN.matcher(b).matches()) {
            throw new InvalidJsonException("bad format number: " + b);
        }
        return b;
    }

    /**
     * Читает null значение из json
     *
     * @param it итератор по json строке
     * @return null
     * @throws InvalidJsonException возникает если встретилось что-то кроме null
     */
    @SuppressWarnings("Duplicates")
    private static String readNull(CharacterIterator it) throws InvalidJsonException {
        StringBuilder sb = new StringBuilder();
        Set<Character> nullChars = new HashSet<>(Arrays.asList('n', 'u', 'l'));
        while (nullChars.contains(it.current())) {
            sb.append(it.current());
            it.next();
        }
        String b = sb.toString();
        if (!b.equals("null")) {
            throw new InvalidJsonException("invalid value: " + b);
        }
        return b;
    }

    /**
     * Читает булевое значение из json
     *
     * @param it итератор по json строке
     * @return Строки "true" или "false"
     * @throws InvalidJsonException возникает если встретилось что-то кроме "true" или "false"
     */
    private static String readBool(CharacterIterator it) throws InvalidJsonException {
        StringBuilder sb = new StringBuilder();
        Set<Character> booleanChar = new HashSet<>(Arrays.asList('t', 'r', 'u', 'e', 'f', 'a', 'l', 's'));
        while (booleanChar.contains(it.current())) {
            sb.append(it.current());
            it.next();
        }
        String b = sb.toString();
        if (!(b.equals("true") || b.equals("false"))) {
            throw new InvalidJsonException("invalid boolean value: " + b);
        }
        return b;
    }

    /**
     * Читает строку из json'a
     *
     * @param it итератор по json строке
     * @return строка
     */
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

    /**
     * Получает очередной ключ в json'е
     *
     * @param it итератор по json строке
     * @return ключ
     */
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

    /**
     * Возвращает вложенный json-объект в виде строки
     *
     * @param it итератор по json строке
     * @return объект
     */
    private static String readObject(CharacterIterator it) {
        return readCompound(it, '{', '}');
    }

    /**
     * Возвращает json-массив в виде строки
     *
     * @param it итератор по json строке
     * @return массив
     */
    private static String readArray(CharacterIterator it) {
        return readCompound(it, '[', ']');
    }


    /**
     * Получает составной json объект
     *
     * @param it         итератор по json строке
     * @param openBrace  начало составного объекта
     * @param closeBrace конец составного объекта
     * @return полученный объект в виде строки
     */
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

    public static void main(String[] args) throws InvalidJsonException {
        Map<String, String> tokens = JSONTokenizer.tokenize("{\"hello\":3423}");
        System.out.println(tokens);
    }
}

/**
 * Исключение возникающее из-за попытки парсинга невалидного json'а
 */
class InvalidJsonException extends Exception {
    public InvalidJsonException(String s) {
        super(s);
    }
}