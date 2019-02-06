package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Withdrawal;

public class PINChecker extends APermissionChecker {
    @Override
    public boolean check(Withdrawal w) {
        if (w.getPinCode() == w.getClient().getPinCode()) {
            return checkNext(w);
        }
        return false;
    }

}
