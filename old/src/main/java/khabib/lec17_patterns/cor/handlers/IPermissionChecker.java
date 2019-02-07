package khabib.lec17_patterns.cor.handlers;

import khabib.lec17_patterns.cor.entities.operations.Operation;

/**
 * Интерфейс авторизаторов
 */
public interface IPermissionChecker {
    /**
     * Функция проверки прав для выполнения операции
     *
     * @param o операция
     * @throws OperationError ошибка возникающаяа при отсутствии прав
     */
    void check(Operation o) throws OperationError;

    /**
     * Установка следующей проверки в цепи
     *
     * @param checker следующая проверка
     * @return полученный результат
     */
    IPermissionChecker setNext(IPermissionChecker checker);
}
