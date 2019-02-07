package khabib.lec17_patterns.cor.entities.operations;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.handlers.BalanceChecker;
import khabib.lec17_patterns.cor.handlers.IPermissionChecker;
import khabib.lec17_patterns.cor.handlers.PINChecker;
import khabib.lec17_patterns.cor.handlers.TargetChecker;

public class Transfer extends Withdrawal {
    private static IPermissionChecker permissionChecker;
    private final String target;

    public Transfer(Client client, int pin, double amount, String target) {
        super(client, pin, amount);
        this.target = target;
    }

    @Override
    public IPermissionChecker getPermissionChecker() {
        if (permissionChecker == null) {
            permissionChecker = new PINChecker()
                    .setNext(new BalanceChecker().setNext(new TargetChecker()));
        }
        return permissionChecker;
    }

    public String getTarget() {
        return target;
    }
}
