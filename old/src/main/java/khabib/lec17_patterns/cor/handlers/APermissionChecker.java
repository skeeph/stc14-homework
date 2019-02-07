package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.operations.Operation;

public abstract class APermissionChecker implements IPermissionChecker {
    protected IPermissionChecker next;

    @Override
    public IPermissionChecker setNext(IPermissionChecker checker) {
        if (next == null) {
            next = checker;
        }
        return this;
    }

    protected void checkNext(Operation w) throws OperationError {
        if (next != null) {
            this.next.check(w);
        }
    }
}
