package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Operation;
import khabib.lec17_patterns.cor.entities.Withdrawal;

public class BalanceChecker extends APermissionChecker {
    @Override
    public boolean check(Operation o) {
        if (!(o instanceof Withdrawal)) {
            return false;
        }
        Withdrawal w = (Withdrawal) o;
        if (w.getClient().getBalance() >= w.getAmount()) {
            return checkNext(w);
        }
        return false;
    }
}
