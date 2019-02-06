package khabib.lec17_patterns.cor.entities;

public class Withdrawal extends Operation {
    private double amount;

    public Withdrawal(Client client, int pinCode, double amount) {
        super(pinCode, client);
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
