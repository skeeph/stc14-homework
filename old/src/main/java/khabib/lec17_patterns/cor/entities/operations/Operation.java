package khabib.lec17_patterns.cor.entities.operations;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.handlers.IPermissionChecker;

public abstract class Operation {
    protected Client client;
    protected int pinCode;

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

    public IPermissionChecker getPermissionChecker() {
        if (getPermCheck() == null) {
            createPermissionChecker();
        }
        return getPermCheck();
    }

    protected abstract void createPermissionChecker();

    protected abstract IPermissionChecker getPermCheck();
}
