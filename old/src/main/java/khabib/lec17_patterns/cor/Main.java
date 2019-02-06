package khabib.lec17_patterns.cor;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.entities.Withdrawal;
import khabib.lec17_patterns.cor.handlers.BalanceChecker;
import khabib.lec17_patterns.cor.handlers.PINChecker;
import khabib.lec17_patterns.cor.handlers.PermissionChecker;

public class Main {
    public static void main(String[] args) {
        PermissionChecker p = new PINChecker();
        p.setNext(new BalanceChecker());

        Client client = new Client(1234, 1480);
        Withdrawal w = new Withdrawal(client, 1234, 1321);
        if (p.check(w)) {
            client.setBalance(client.getBalance() - w.getAmount());
            System.out.println(client);
        }

        w = new Withdrawal(client, 1234, 200);
        if (p.check(w)) {
            client.setBalance(client.getBalance() - w.getAmount());
            System.out.println(client);
        } else {
            System.out.println("Operation is forbidden");
        }
    }
}
