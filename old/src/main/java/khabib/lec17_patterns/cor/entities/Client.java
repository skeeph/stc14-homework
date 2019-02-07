package khabib.lec17_patterns.cor.entities;

/**
 * Класс представяет собой клиента банка
 */
public class Client {
    private int pinCode;
    private double balance;

    /**
     * Создает клиента с заданным балансом и пин кодом
     *
     * @param pinCode пин код
     * @param balance баланс счета клиента
     */
    public Client(int pinCode, double balance) {
        this.pinCode = pinCode;
        this.balance = balance;
    }

    /**
     * @return пин код карты клиента
     */
    public int getPinCode() {
        return pinCode;
    }

    /**
     * @param pinCode Установка нового пин кода
     */
    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    /**
     * @return Баланс пользователя
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Обновление баланса пользователя
     *
     * @param balance новый баланс
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "pinCode=" + pinCode +
                ", balance=" + balance +
                '}';
    }
}
