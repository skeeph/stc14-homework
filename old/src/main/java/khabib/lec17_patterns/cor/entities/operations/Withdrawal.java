package khabib.lec17_patterns.cor.entities.operations;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.handlers.BalanceChecker;
import khabib.lec17_patterns.cor.handlers.IPermissionChecker;
import khabib.lec17_patterns.cor.handlers.PINChecker;

/**
 * Операция снятия наличных
 */
public class Withdrawal extends PINRequired {
    private static IPermissionChecker permissionChecker;
    private double amount;

    /**
     * @param client  клиент
     * @param pinCode пин код
     * @param amount  снимаемая сумма
     */
    public Withdrawal(Client client, int pinCode, double amount) {
        super(client, pinCode);
        this.amount = amount;
    }

    /**
     * @return сумма для снятия
     */
    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Withdrawal{" + "amount=" + amount +
                ", client=" + client +
                '}';
    }

    /**
     * Создания авторизатора
     */
    @Override
    protected void createPermissionChecker() {
        permissionChecker = new PINChecker(new BalanceChecker());
    }

    /**
     * @return авторизатор
     */
    @Override
    protected IPermissionChecker getPermCheck() {
        return permissionChecker;
    }
}
