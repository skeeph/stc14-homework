package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Withdrawal;

public interface PermissionChecker {
    boolean check(Withdrawal w);

    void setNext(PermissionChecker checker);
}
