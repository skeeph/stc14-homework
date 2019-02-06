package khabib.lec17_patterns.cor;

import khabib.lec17_patterns.cor.entities.ATM;
import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.handlers.BalanceChecker;
import khabib.lec17_patterns.cor.handlers.OperationError;
import khabib.lec17_patterns.cor.handlers.PINChecker;
import khabib.lec17_patterns.cor.handlers.PermissionChecker;

public class Main {
    public static void main(String[] args) {
        PermissionChecker p = new PINChecker();
        p.setNext(new BalanceChecker());

        Client client = new Client(1234, 1480);
        ATM atm = new ATM();
        try {
            atm.transferMoney(client, 1234, 120, "5469600012611380");
            System.out.println(client);
        } catch (OperationError e) {
            System.err.println(e.getMessage());
        }

    }
}
