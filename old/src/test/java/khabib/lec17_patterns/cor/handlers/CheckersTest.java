package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Client;
import org.junit.jupiter.api.BeforeEach;

public abstract class CheckersTest {
    protected PermissionChecker checker;
    protected Client client;

    @BeforeEach
    void setUp() {
        client = new Client(1234, 100);
    }
}
