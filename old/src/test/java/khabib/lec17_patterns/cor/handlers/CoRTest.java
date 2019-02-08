package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.entities.operations.PINRequired;
import khabib.lec17_patterns.cor.entities.operations.Withdrawal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест проверяет корректную работу реализации паттерна
 * Цепочка обязанностей. Хоть это не совсем модульный тест,
 * все же решил добавить его
 */
public class CoRTest {
    private IPermissionChecker p;
    private Client client;

    @BeforeEach
    void setUp() {
        p = new PINChecker(new BalanceChecker());
        client = new Client(1234, 100);
    }

    @Test
    void testCoR() {
        Throwable e = assertThrows(OperationError.class,
                () -> p.check(new Withdrawal(client, 1235, 100)));
        assertEquals("Invalid PIN code", e.getMessage());

        e = assertThrows(OperationError.class,
                () -> p.check(new Withdrawal(client, 1234, 200)));
        assertEquals("Сумма операции превосходит баланс пользователя", e.getMessage());

        e = assertThrows(OperationError.class,
                () -> p.check(new PINRequired(client, 1234)));
        assertEquals("Неверный тип операции: " + PINRequired.class.getSimpleName(), e.getMessage());
        assertDoesNotThrow(() -> p.check(new Withdrawal(client, 1234, 50)));
    }
}
