package khabib.lec17_patterns.cor;

import khabib.lec17_patterns.cor.entities.ATM;
import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.handlers.OperationError;

/**
 * Класс с главной функцией.
 *
 * @author Khabib Murtuzaaliev
 */
public class Main {
    public static void main(String[] args) {

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
