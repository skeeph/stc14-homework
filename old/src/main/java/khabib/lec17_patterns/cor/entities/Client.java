package khabib.lec17_patterns.cor.entities;

public class Client {
    private int pinCode;
    private double balance;

    public Client(int pinCode, double balance) {
        this.pinCode = pinCode;
        this.balance = balance;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public double getBalance() {
        return balance;
    }

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
