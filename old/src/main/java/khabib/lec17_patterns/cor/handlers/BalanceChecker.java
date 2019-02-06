package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Withdrawal;

public class BalanceChecker extends APermissionChecker {
    @Override
    public boolean check(Withdrawal w) {
        if (w.getClient().getBalance() >= w.getAmount()) {
            return checkNext(w);
        }
        return false;
    }
}
