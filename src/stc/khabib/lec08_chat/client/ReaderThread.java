package stc.khabib.lec08_chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketTimeoutException;

public class ReaderThread extends Thread {
    BufferedReader reader;

    public ReaderThread(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        String serverWord; // ждём, что скажет сервер
        try {
            serverWord = reader.readLine();
            System.out.println(serverWord); // получив - выводим на экран
            while (!isInterrupted()) {
                try {
                    serverWord = reader.readLine(); // ждём, что скажет сервер
                    System.out.println(serverWord); // получив - выводим на экран
                } catch (SocketTimeoutException ignored) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
