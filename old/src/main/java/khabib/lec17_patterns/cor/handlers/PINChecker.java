package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.operations.Operation;

/**
 * Класс проверяет введенный пин код
 */
public class PINChecker extends APermissionChecker {
    public PINChecker() {
    }

    public PINChecker(IPermissionChecker next) {
        this.setNext(next);
    }

    /**
     * сравнивает введенный пин код с актуальным кодом клиента
     *
     * @param o операция
     * @throws OperationError неверный пин код
     */
    @Override
    public void check(Operation o) throws OperationError {
        if (o.getPinCode() == o.getClient().getPinCode()) {
            checkNext(o);
            return;
        }
        throw new OperationError("Invalid PIN code");
    }

}
