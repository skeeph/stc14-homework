package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Operation;
import khabib.lec17_patterns.cor.entities.Withdrawal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Проверка баланса
 */
class BalanceCheckerTest extends CheckersTest {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        checker = new BalanceChecker();
    }

    /**
     * Тест проверяет то что нельзя снять со счета больше денег чем там лежит.
     */
    @Test
    void check() {
        assertThrows(OperationError.class,
                () -> checker.check(
                        new Operation(client, 1235)));
        assertThrows(OperationError.class,
                () -> checker.check(
                        new Withdrawal(client, 1234, 200)));
        assertDoesNotThrow(
                () -> checker.check(
                        new Withdrawal(client, 1234, 40)));
    }
}