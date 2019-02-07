package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.operations.Operation;

public interface IPermissionChecker {
    void check(Operation o) throws OperationError;

    IPermissionChecker setNext(IPermissionChecker checker);
}
