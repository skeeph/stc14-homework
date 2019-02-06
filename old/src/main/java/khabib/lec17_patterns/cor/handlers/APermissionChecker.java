package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.operations.Operation;

public abstract class APermissionChecker implements IPermissionChecker {
    protected IPermissionChecker next;

    @Override
    public void setNext(IPermissionChecker checker) {
        if (next == null) {
            next = checker;
        }
    }

    protected void checkNext(Operation w) throws OperationError {
        if (next != null) {
            this.next.check(w);
        }
    }
}
