package khabib.lec17_patterns.cor.entities.operations;

import khabib.lec17_patterns.cor.entities.Client;

public class Transfer extends Withdrawal {
    private final String target;

    public Transfer(Client client, int pin, double amount, String target) {
        super(client, pin, amount);
        this.target = target;
    }

    public String getTarget() {
        return target;
    }
}
