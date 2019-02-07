package khabib.lec17_patterns.cor.entities.operations;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.handlers.BalanceChecker;
import khabib.lec17_patterns.cor.handlers.IPermissionChecker;
import khabib.lec17_patterns.cor.handlers.PINChecker;

public class Withdrawal extends PINRequired {
    private static IPermissionChecker permissionChecker;
    private double amount;

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
    protected void createPermissionChecker() {
        permissionChecker = new PINChecker().setNext(new BalanceChecker());
    }

    @Override
    protected IPermissionChecker getPermCheck() {
        return permissionChecker;
    }
}
