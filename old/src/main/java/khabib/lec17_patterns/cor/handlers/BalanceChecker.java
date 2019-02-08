package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.operations.Operation;
import khabib.lec17_patterns.cor.entities.operations.Withdrawal;

/**
 * Класс проверяющий баланс пользователя
 */
public class BalanceChecker extends APermissionChecker {
    public BalanceChecker() {
    }

    public BalanceChecker(IPermissionChecker next) {
        this.setNext(next);
    }

    /**
     * Проверяет достаточно ли средств у клиента для выполнения операции
     *
     * @param o Операция
     * @throws OperationError не хватает на денег на счету
     */
    @Override
    public void check(Operation o) throws OperationError {
        if (!(o instanceof Withdrawal)) {
            throw new OperationError("Неверный тип операции: " + o.getClass().getSimpleName());
        }
        Withdrawal w = (Withdrawal) o;
        if (w.getClient().getBalance() >= w.getAmount()) {
            checkNext(w);
            return;
        }
        throw new OperationError("Сумма операции превосходит баланс пользователя");
    }
}
