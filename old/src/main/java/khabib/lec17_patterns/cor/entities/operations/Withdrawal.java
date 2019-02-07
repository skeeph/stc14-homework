package khabib.lec17_patterns.cor.entities.operations;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.handlers.BalanceChecker;
import khabib.lec17_patterns.cor.handlers.IPermissionChecker;
import khabib.lec17_patterns.cor.handlers.PINChecker;

public class Withdrawal extends Operation {
    private double amount;
    private static IPermissionChecker permissionChecker;
    public Withdrawal(Client client, int pinCode, double amount) {
        super(client, pinCode);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Withdrawal{" + "amount=" + amount +
                ", client=" + client +
                '}';
    }

    @Override
    public IPermissionChecker getPermissionChecker() {
        if (permissionChecker == null) {
            permissionChecker = new PINChecker().setNext(new BalanceChecker());
        }
        return permissionChecker;
    }
}
