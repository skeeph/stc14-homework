package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Operation;
import khabib.lec17_patterns.cor.entities.Transfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TargetCheckerTest extends CheckersTest {

    @BeforeEach
    void setUp() {
        checker = new TargetChecker();
    }

    @Test
    void check() {
        assertTrue(checker.check(new Transfer(client, 1000, 1000, "4111 1111 1111 1111")));
        assertTrue(checker.check(new Transfer(client, 1000, 1000, "4111111111111111")));
        assertFalse(checker.check(new Transfer(client, 100, 1000, "456464")));
        assertFalse(checker.check(new Transfer(client, 100, 1000, "some target id")));
        assertFalse(checker.check(new Operation(client, 100)));
    }
}