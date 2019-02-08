package khabib.lec17_patterns.cor.entities.operations;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.handlers.BalanceChecker;
import khabib.lec17_patterns.cor.handlers.IPermissionChecker;
import khabib.lec17_patterns.cor.handlers.PINChecker;
import khabib.lec17_patterns.cor.handlers.TargetChecker;

/**
 * Операция перевода денег на другой счет
 */
public class Transfer extends Withdrawal {
    private static IPermissionChecker permissionChecker;
    private final String target;

    /**
     * @param client клиент
     * @param pin    пин код
     * @param amount сумма перевода
     * @param target счет получателя
     */
    public Transfer(Client client, int pin, double amount, String target) {
        super(client, pin, amount);
        this.target = target;
    }

    /**
     * Создание авторизатора
     */
    @Override
    protected void createPermissionChecker() {
        permissionChecker = new PINChecker(new BalanceChecker(new TargetChecker()));
    }

    /**
     * @return Авторизатор
     */
    @Override
    protected IPermissionChecker getPermCheck() {
        return permissionChecker;
    }

    /**
     * @return Счет получателя
     */
    public String getTarget() {
        return target;
    }
}
