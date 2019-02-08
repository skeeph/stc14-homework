package khabib.lec17_patterns.cor.entities;

import khabib.lec17_patterns.cor.entities.operations.PINRequired;
import khabib.lec17_patterns.cor.entities.operations.Transfer;
import khabib.lec17_patterns.cor.entities.operations.Withdrawal;
import khabib.lec17_patterns.cor.handlers.OperationError;

/**
 * Класс симулирующий работу банкомата
 */
public class ATM {
    /**
     * Переводит деньги на другой счет
     *
     * @param client клиент
     * @param pin    пин код клиента
     * @param amount переводимая сумма
     * @param target номер счета получателя
     * @throws OperationError Нет возможности совершить операцию
     */
    public void transferMoney(Client client, int pin, double amount, String target) throws OperationError {
        Transfer t = new Transfer(client, pin, amount, target);
        t.getPermissionChecker().check(t);
        client.setBalance(client.getBalance() - t.getAmount());
        System.out.printf("Перевод суммы %f на счет %s:", t.getAmount(), t.getTarget());
    }

    /**
     * Снятие наличных со счета клиента.
     * Выводит в консоль величину снятой суммы и уменьшает баланс пользователя на эту сумму
     *
     * @param client клиент
     * @param pin    пин код
     * @param amount снимаемая сумма
     * @throws OperationError Нет возможности совершить операцию
     */
    public void withdraw(Client client, int pin, double amount) throws OperationError {
        Withdrawal w = new Withdrawal(client, pin, amount);
        w.getPermissionChecker().check(w);
        client.setBalance(client.getBalance() - w.getAmount());
        System.out.println("Выдача наличности: " + w.getAmount());
    }

    /**
     * Изменение пин кода клиента
     *
     * @param client клиент
     * @param pin    пин код(используется для авторизации)
     * @param newPin Новый пин код
     * @throws OperationError возникает когда у пользователя нет прав для выполнения операции
     */
    public void changePin(Client client, int pin, int newPin) throws OperationError {
        PINRequired o = new PINRequired(client, pin);
        o.getPermissionChecker().check(o);
        client.setPinCode(newPin);
        System.out.println("Установка нового кода: " + newPin);
    }
}
