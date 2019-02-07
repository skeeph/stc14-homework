package khabib.lec17_patterns.cor.entities.operations;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.handlers.IPermissionChecker;
import khabib.lec17_patterns.cor.handlers.PINChecker;

/**
 * Операция требующая ввода Пин кода
 */
public class PINRequired extends Operation {
    private static IPermissionChecker permissionChecker;

    /**
     * @param client  клиент
     * @param pinCode введенный пин код
     */
    public PINRequired(Client client, int pinCode) {
        super(client, pinCode);
    }

    /**
     * Создание авторизатора
     */
    @Override
    protected void createPermissionChecker() {
        permissionChecker = new PINChecker();
    }

    /**
     * Геттер авторизатора
     * @return Авторизатор
     */
    @Override
    protected IPermissionChecker getPermCheck() {
        return permissionChecker;
    }
}
