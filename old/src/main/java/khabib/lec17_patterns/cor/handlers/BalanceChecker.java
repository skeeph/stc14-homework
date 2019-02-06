package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.Operation;
import khabib.lec17_patterns.cor.entities.Withdrawal;

public class BalanceChecker extends APermissionChecker {
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
