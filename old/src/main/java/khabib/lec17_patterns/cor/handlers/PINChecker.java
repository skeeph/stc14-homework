package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Operation;

public class PINChecker extends APermissionChecker {
    @Override
    public boolean check(Operation w) {
        if (w.getPinCode() == w.getClient().getPinCode()) {
            return checkNext(w);
        }
        return false;
    }

}
