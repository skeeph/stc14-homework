package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.operations.Operation;

public abstract class APermissionChecker implements IPermissionChecker {
    protected IPermissionChecker next;


    /**
     * Установка следующей проверки в цепи
     *
     * @param checker следующая проверка
     */
    protected void setNext(IPermissionChecker checker) {
        if (next == null) {
            next = checker;
        }
    }

    /**
     * Выполнение следующей проверки в цепи
     *
     * @param w операция, для которой выполняется проверка
     * @throws OperationError нет прав для операции
     */
    protected void checkNext(Operation w) throws OperationError {
        if (next != null) {
            this.next.check(w);
        }
    }
}
