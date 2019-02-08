package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.operations.PINRequired;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertDoesNotThrow(() -> checker.check(new PINRequired(client, 1234)));
        assertThrows(OperationError.class, () -> checker.check(
                new PINRequired(client, 1235)
        ));
    }
}