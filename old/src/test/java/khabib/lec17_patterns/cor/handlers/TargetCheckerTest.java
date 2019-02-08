package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.operations.PINRequired;
import khabib.lec17_patterns.cor.entities.operations.Transfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TargetCheckerTest extends CheckersTest {

    @BeforeEach
    void setUp() {
        checker = new TargetChecker();
    }

    @Test
    void check() {
        assertDoesNotThrow(() -> checker.check(
                new Transfer(client, 1, 1, "4111 1111 1111 1111")));
        assertDoesNotThrow(() -> checker.check(
                new Transfer(client, 1, 1, "4111111111111111")));
        assertThrows(OperationError.class,
                () -> checker.check(new Transfer(client, 100, 1000, "456464")));
        assertThrows(OperationError.class,
                () -> checker.check(new Transfer(client, 100, 1000, "some target id")));
        assertThrows(OperationError.class,
                () -> checker.check(new PINRequired(client, 100)));
    }
}