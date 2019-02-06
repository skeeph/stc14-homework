package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.entities.Operation;
import khabib.lec17_patterns.cor.entities.Withdrawal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Проверка баланса
 */
class BalanceCheckerTest {
    private BalanceChecker checker;
    private Client client;

    @BeforeEach
    void setUp() {
        checker = new BalanceChecker();
        client = new Client(1234, 100);
    }

    /**
     * Тест проверяет то что нельзя снять со счета больше денег чем там лежит.
     */
    @Test
    void check() {
        assertFalse(checker.check(new Operation(client, 1235)));
        assertFalse(checker.check(new Withdrawal(client, 1234, 200)));
        assertTrue(checker.check(new Withdrawal(client, 1234, 40)));
    }
}