package khabib.lec23;

import khabib.lec23.api.Handler;

public class Main {
    public static void main(String[] args) {
        Handler handler = new DataHandler();
        System.out.println("Результат: " +
                handler.process("Source", "Dest"));
    }
}
