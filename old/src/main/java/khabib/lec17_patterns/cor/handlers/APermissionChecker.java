package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Operation;

public abstract class APermissionChecker implements PermissionChecker {
    protected PermissionChecker next;

    @Override
    public void setNext(PermissionChecker checker) {
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
