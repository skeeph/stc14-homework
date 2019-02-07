package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.operations.Operation;
import khabib.lec17_patterns.cor.entities.operations.Transfer;

/**
 * Проверка корректности введенного счета получателя
 */
public class TargetChecker extends APermissionChecker {
    @Override
    public void check(Operation o) throws OperationError {
        if (!(o instanceof Transfer)) {
            throw new OperationError("Неверный тип операции: " + o.getClass().getSimpleName());
        }
        Transfer t = (Transfer) o;
        if (t.getTarget().matches("(\\d{4}\\s*){4}")) {
            checkNext(o);
            return;
        }
        throw new OperationError("Неверный формат счета получателя");
    }
}
