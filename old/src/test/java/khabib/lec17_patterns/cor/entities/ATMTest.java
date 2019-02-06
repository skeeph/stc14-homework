package khabib.lec17_patterns.cor.entities;

import khabib.lec17_patterns.cor.handlers.OperationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ATMTest {
    private ATM atm;
    private Client client;

    @BeforeEach
    void setUp() {
        atm = new ATM();
        client = new Client(2000, 20000);
    }

    @Test
    void transferMoney() {
        assertDoesNotThrow(() -> atm.transferMoney(client, 2000, 5000, "4111411141114111"));
        assertEquals(15000, client.getBalance());
    }

    @Test
    void withdraw() {
        assertDoesNotThrow(() -> atm.withdraw(client, 2000, 10000));
        assertEquals(10000, client.getBalance());
    }

    @Test
    void changePin() {
        assertDoesNotThrow(() -> atm.changePin(client, 2000, 4114));
        assertEquals(4114, client.getPinCode());
    }

    @Test
    void transferMoneyNegative() {
        Throwable e = assertThrows(OperationError.class,
                () -> atm.transferMoney(client, 1000, 5000,
                        "4111411141114111"));
        assertEquals("Invalid PIN code", e.getMessage());

        e = assertThrows(OperationError.class,
                () -> atm.transferMoney(client, 2000, 50000,
                        "4111411141114111"));
        assertEquals("Сумма операции превосходит баланс пользователя", e.getMessage());

        e = assertThrows(OperationError.class,
                () -> atm.transferMoney(client, 2000, 5000,
                        "411"));
        assertEquals("Неверный формат счета получателя", e.getMessage());
    }

    @Test
    void withdrawNegative() {
        Throwable e = assertThrows(OperationError.class,
                () -> atm.withdraw(client, 1000, 5000));
        assertEquals("Invalid PIN code", e.getMessage());

        e = assertThrows(OperationError.class,
                () -> atm.withdraw(client, 2000, 50000));
        assertEquals("Сумма операции превосходит баланс пользователя", e.getMessage());
    }

    @Test
    void changePinNegative() {
        Throwable e = assertThrows(OperationError.class,
                () -> atm.changePin(client, 1000, 5000));
        assertEquals("Invalid PIN code", e.getMessage());
    }
}