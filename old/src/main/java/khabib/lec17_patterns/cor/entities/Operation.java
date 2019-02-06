package khabib.lec17_patterns.cor.entities;

public abstract class Operation {
    protected Client client;
    private int pinCode;

    protected Operation(int pinCode, Client client) {
        this.pinCode = pinCode;
        this.client = client;
    }

    public int getPinCode() {
        return pinCode;
    }

    public Client getClient() {
        return client;
    }
}
