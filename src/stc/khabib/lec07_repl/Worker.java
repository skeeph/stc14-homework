package stc.khabib.lec07_repl;

/**
 * Интерфейс для реализации в рантайме.
 */
public interface Worker {
    /**
     * Метод, тело которого нам нужно ввести из консоли.
     *
     * @param a первый аргумент
     * @param b второй аргумент
     */
    void doWork(String a, String b);
}
