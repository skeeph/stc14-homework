package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Operation;
import khabib.lec17_patterns.cor.entities.Transfer;

public class TargetChecker extends APermissionChecker {

    @Override
    public boolean check(Operation o) {
        if (!(o instanceof Transfer)) {
            return false;
        }
        Transfer t = (Transfer) o;
        return t.getTarget().matches("(\\d{4}\\s*){4}");
    }
}
