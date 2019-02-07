package khabib.lec17_patterns.cor.entities;

import khabib.lec17_patterns.cor.entities.operations.PINRequired;
import khabib.lec17_patterns.cor.entities.operations.Transfer;
import khabib.lec17_patterns.cor.entities.operations.Withdrawal;
import khabib.lec17_patterns.cor.handlers.OperationError;

public class ATM {
    public void transferMoney(Client client, int pin, double amount, String target) throws OperationError {
        Transfer t = new Transfer(client, pin, amount, target);
        t.getPermissionChecker().check(t);
        client.setBalance(client.getBalance() - t.getAmount());
        System.out.printf("Перевод суммы %f на счет %s:", t.getAmount(), t.getTarget());
    }

    public void withdraw(Client client, int pin, double amount) throws OperationError {
        Withdrawal w = new Withdrawal(client, pin, amount);
        w.getPermissionChecker().check(w);
        client.setBalance(client.getBalance() - w.getAmount());
        System.out.println("Выдача наличности: " + w.getAmount());
    }

    public void changePin(Client client, int pin, int newPin) throws OperationError {
        PINRequired o = new PINRequired(client, pin);
        o.getPermissionChecker().check(o);
        client.setPinCode(newPin);
        System.out.println("Установка нового кода: " + newPin);
    }
}
