package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тест для задачи проверки PIN кода
 */
class PINCheckerTest extends CheckersTest {
    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        checker = new PINChecker();
    }

    /**
     * Проверка корректности роботы проверки пин кода
     */
    @Test
    void check() {
        assertTrue(checker.check(new Operation(client, 1234)));
        assertFalse(checker.check(new Operation(client, 1235)));
    }
}