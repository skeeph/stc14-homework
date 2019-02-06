package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Operation;

public class PINChecker extends APermissionChecker {
    @Override
    public void check(Operation o) throws OperationError {
        if (o.getPinCode() == o.getClient().getPinCode()) {
            checkNext(o);
            return;
        }
        throw new OperationError("Invalid PIN code");
    }

}
