package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Operation;

public interface PermissionChecker {
    boolean check(Operation w);

    void setNext(PermissionChecker checker);
}
