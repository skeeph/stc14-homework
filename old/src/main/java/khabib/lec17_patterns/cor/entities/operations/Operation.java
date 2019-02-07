package khabib.lec17_patterns.cor.entities.operations;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.handlers.IPermissionChecker;
import khabib.lec17_patterns.cor.handlers.PINChecker;

public class Operation {
    private static IPermissionChecker permissionChecker;
    protected Client client;
    private int pinCode;

    public Operation(Client client, int pinCode) {
        this.pinCode = pinCode;
        this.client = client;
    }

    public IPermissionChecker getPermissionChecker() {
        if (permissionChecker == null) {
            permissionChecker = new PINChecker();
        }
        return permissionChecker;
    }

    public int getPinCode() {
        return pinCode;
    }

    public Client getClient() {
        return client;
    }
}
