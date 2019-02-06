package khabib.lec17_patterns.cor.entities.operations;

import khabib.lec17_patterns.cor.entities.Client;

public class Operation {
    protected Client client;
    private int pinCode;

    public Operation(Client client, int pinCode) {
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
