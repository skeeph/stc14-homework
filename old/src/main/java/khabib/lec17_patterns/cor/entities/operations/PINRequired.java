package khabib.lec17_patterns.cor.entities.operations;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.handlers.IPermissionChecker;
import khabib.lec17_patterns.cor.handlers.PINChecker;

public class PINRequired extends Operation {
    private static IPermissionChecker permissionChecker;

    public PINRequired(Client client, int pinCode) {
        super(client, pinCode);
    }

    @Override
    protected void createPermissionChecker() {
        permissionChecker = new PINChecker();
    }

    @Override
    protected IPermissionChecker getPermCheck() {
        return permissionChecker;
    }
}
