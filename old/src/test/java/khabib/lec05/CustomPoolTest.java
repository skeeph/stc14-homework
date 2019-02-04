package khabib.lec05;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CustomPoolTest {
    private CustomPool pool;

    @BeforeEach
    void setUp() {
        pool = new CustomPool(2);
    }

    @AfterEach
    void tearDown() {
        pool.shutdown();
    }

    @Test
    void submit() throws InterruptedException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        pool.submit(() -> System.out.println(Thread.currentThread().getName()));
        Thread.sleep(1000);
        String printed = out.toString().trim();
        assertTrue(printed.equals("Worker-0") || printed.equals("Worker-1"));
    }

    @Test
    void submitException() {
        pool.shutdown();
        assertThrows(InterruptedException.class, () -> pool.submit(() -> System.out.println(1)));
    }

    @Test
    void testShutdown() {
        assertFalse(pool.isShutdown());
        pool.shutdown();
        assertTrue(pool.isShutdown());
    }
}