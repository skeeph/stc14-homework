package khabib.lec17_patterns.cor.entities;

import khabib.lec17_patterns.cor.entities.operations.Operation;
import khabib.lec17_patterns.cor.entities.operations.Transfer;
import khabib.lec17_patterns.cor.entities.operations.Withdrawal;
import khabib.lec17_patterns.cor.handlers.*;

public class ATM {
    private final IPermissionChecker PINChecker;
    private final IPermissionChecker WithdrawChecker;
    private final IPermissionChecker TransferChecker;

    public ATM() {
        PINChecker = new PINChecker();
        WithdrawChecker = new PINChecker();
        WithdrawChecker.setNext(new BalanceChecker());

        TransferChecker = new PINChecker();
        BalanceChecker b = new BalanceChecker();
        b.setNext(new TargetChecker());
        TransferChecker.setNext(b);
    }

    public void transferMoney(Client client, int pin, double amount, String target) throws OperationError {
        Transfer t = new Transfer(client, pin, amount, target);
        TransferChecker.check(t);
        client.setBalance(client.getBalance() - t.getAmount());
        System.out.printf("Перевод суммы %f на счет %s:", t.getAmount(), t.getTarget());
    }

    public void withdraw(Client client, int pin, double amount) throws OperationError {
        Withdrawal w = new Withdrawal(client, pin, amount);
        WithdrawChecker.check(w);
        client.setBalance(client.getBalance() - w.getAmount());
        System.out.println("Выдача наличности: " + w.getAmount());
    }

    public void changePin(Client client, int pin, int newPin) throws OperationError {
        PINChecker.check(new Operation(client, pin));
        client.setPinCode(newPin);
        System.out.println("Установка нового кода: " + newPin);
    }
}
