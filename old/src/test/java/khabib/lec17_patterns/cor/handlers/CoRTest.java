package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.entities.Operation;
import khabib.lec17_patterns.cor.entities.Withdrawal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тест проверяет корректную работу реализации паттерна
 * Цепочка обязанностей. Хоть это не совсем модульный тест,
 * все же решил добавить его
 */
public class CoRTest {
    private PermissionChecker p;
    private Client client;

    @BeforeEach
    void setUp() {
        p = new PINChecker();
        p.setNext(new BalanceChecker());
        client = new Client(1234, 100);
    }

    @Test
    void testCoR() {
        assertFalse(p.check(new Withdrawal(client, 1235, 100)));
        assertFalse(p.check(new Withdrawal(client, 1234, 200)));
        assertFalse(p.check(new Operation(client, 1234)));
        assertTrue(p.check(new Withdrawal(client, 1234, 50)));
    }
}
