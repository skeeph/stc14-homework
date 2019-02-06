package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Operation;

public class PINChecker extends APermissionChecker {
    @Override
    public boolean check(Operation o) {
        if (o.getPinCode() == o.getClient().getPinCode()) {
            return checkNext(o);
        }
        return false;
    }

}
