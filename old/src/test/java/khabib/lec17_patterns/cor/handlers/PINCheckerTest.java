package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.entities.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тест для задачи проверки PIN кода
 */
class PINCheckerTest {
    private PINChecker p;
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client(1234, 100500);
        p = new PINChecker();
    }

    /**
     * Проверка корректности роботы проверки пин кода
     */
    @Test
    void check() {
        assertTrue(p.check(new Operation(client, 1234)));
        assertFalse(p.check(new Operation(client, 1235)));
    }
}