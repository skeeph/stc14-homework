package khabib.lec17_patterns.cor.entities;

import khabib.lec17_patterns.cor.handlers.BalanceChecker;
import khabib.lec17_patterns.cor.handlers.PINChecker;
import khabib.lec17_patterns.cor.handlers.PermissionChecker;
import khabib.lec17_patterns.cor.handlers.TargetChecker;

public class ATM {
    private final PermissionChecker PINChecker;
    private final PermissionChecker WithdrawChecker;
    private final PermissionChecker TransferChecker;

    public ATM() {
        PINChecker = new PINChecker();
        WithdrawChecker = new PINChecker();
        WithdrawChecker.setNext(new BalanceChecker());

        TransferChecker = new PINChecker();
        BalanceChecker b = new BalanceChecker();
        b.setNext(new TargetChecker());
        TransferChecker.setNext(b);
    }

    public void transferMoney(Client client, int pin, double amount, String target) {
        Transfer t = new Transfer(client, pin, amount, target);
        if (TransferChecker.check(t)) {
            client.setBalance(client.getBalance() - t.getAmount());
            System.out.printf("Перевод суммы %f на счет %s:", t.getAmount(), t.getTarget());
        } else {
            //TODO 06.02.19 skeeph: Рефракторить это
            System.err.println("Ошибка перевода денег");
        }
    }

    public void withdraw(Client client, int pin, double amount) {
        Withdrawal w = new Withdrawal(client, pin, amount);
        if (WithdrawChecker.check(w)) {
            client.setBalance(client.getBalance() - w.getAmount());
            System.out.println("Выдача наличности: " + w.getAmount());
        } else {
            //TODO 06.02.19 skeeph: Рефракторить это
            System.err.println("Ошибка доступа");
        }
    }

    public void changePin(Client client, int pin, int newPin) {
        if (PINChecker.check(new Operation(client, pin))) {
            client.setPinCode(newPin);
            System.out.println("Установка нового кода: " + newPin);
        } else {
            //TODO 06.02.19 skeeph: Рефракторить это
            System.err.println("Ошибка доступа");
        }
    }
}
