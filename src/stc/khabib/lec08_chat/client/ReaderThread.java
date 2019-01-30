package stc.khabib.lec08_chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Класс реализует в клиенте отдельный поток, получающий сообщения с сервера.
 */
public class ReaderThread extends Thread {
    BufferedReader reader;

    /**
     * Конструктор
     *
     * @param reader объект для чтения из сокета
     */
    public ReaderThread(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * В бесконечном циле получать сообщения с сервера
     * и печатать их в консоль
     */
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
