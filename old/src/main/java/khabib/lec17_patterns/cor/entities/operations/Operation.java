package khabib.lec17_patterns.cor.entities.operations;

import khabib.lec17_patterns.cor.entities.Client;
import khabib.lec17_patterns.cor.handlers.IPermissionChecker;

/**
 * Абстрактный класс - предок всех операций
 */
public abstract class Operation {
    protected Client client;
    protected int pinCode;

    /**
     * @param client  клиент
     * @param pinCode пин код клиента
     */
    public Operation(Client client, int pinCode) {
        this.pinCode = pinCode;
        this.client = client;
    }

    /**
     * @return Пин код
     */
    public int getPinCode() {
        return pinCode;
    }

    /**
     * @return Клиент
     */
    public Client getClient() {
        return client;
    }

    /**
     * Возвращает объект проверяющий разрешения на выполнение операции
     * @return проверяющий объект
     */
    public IPermissionChecker getPermissionChecker() {
        if (getPermCheck() == null) {
            createPermissionChecker();
        }
        return getPermCheck();
    }

    /**
     * Создает "авторизатор"
     */
    protected abstract void createPermissionChecker();

    /**
     * @return геттер авторизатора
     */
    protected abstract IPermissionChecker getPermCheck();
}
