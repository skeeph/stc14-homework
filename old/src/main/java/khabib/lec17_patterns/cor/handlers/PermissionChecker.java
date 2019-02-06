package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.operations.Operation;

public interface PermissionChecker {
    void check(Operation o) throws OperationError;
    void setNext(PermissionChecker checker);
}
