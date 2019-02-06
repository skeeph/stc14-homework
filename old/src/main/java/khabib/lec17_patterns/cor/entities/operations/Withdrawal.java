package khabib.lec17_patterns.cor.entities.operations;

import khabib.lec17_patterns.cor.entities.Client;

public class Withdrawal extends Operation {
    private double amount;

    public Withdrawal(Client client, int pinCode, double amount) {
        super(client, pinCode);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Withdrawal{" + "amount=" + amount +
                ", client=" + client +
                '}';
    }
}
