package khabib.lec05;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестирование кастомного тренд пула
 */
class CustomPoolTest {
    private CustomPool pool;

    /**
     * Выполняется перед каждым тестом
     */
    @BeforeEach
    void setUp() {
        pool = new CustomPool(2);
    }

    /**
     * Выполняется после каждого теста
     */
    @AfterEach
    void tearDown() {
        pool.shutdown();
    }

    /**
     * Тест успешного выполнения добавленных задач
     *
     * @throws InterruptedException остановлена задача
     */
    @Test
    void submit() throws InterruptedException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        Thread.sleep(1000);
        String printed = out.toString().trim();
        assertTrue(printed.equals("Worker-0") || printed.equals("Worker-1"));
    }

    /**
     * Негативный тест добавления и выполнения задач
     */
    @Test
    void submitException() {
        pool.shutdown();
        assertThrows(InterruptedException.class, () -> pool.submit(() -> System.out.println(1)));
    }

    /**
     * Тест отключения тред пула
     */
    @Test
    void testShutdown() {
        assertFalse(pool.isShutdown());
        pool.shutdown();
        assertTrue(pool.isShutdown());
    }
}