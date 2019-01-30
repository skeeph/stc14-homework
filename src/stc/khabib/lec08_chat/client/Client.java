package stc.khabib.lec08_chat.client;

import java.io.*;
import java.net.Socket;

/**
 * Чат-клиент
 */
public class Client {
    public static void main(String[] args) throws IOException {
        try (
                Socket clientSocket = new Socket("localhost", 4004);
                BufferedReader socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ) {
            clientSocket.setSoTimeout(1000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Привет! Как вас зовут?");
            String name = reader.readLine();

            out.write(name.trim() + "\n"); // отправляем сообщение на сервер
            out.flush();

            Thread clientReader = new ReaderThread(socketIn);
            clientReader.start();
            String word;
            while (!(word = reader.readLine()).equals("bye")) {
                out.write(word + "\n"); // отправляем сообщение на сервер
                out.flush();
            }
            clientReader.interrupt();
            clientReader.join();
            System.out.println("You typed `bye`, shutting down");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally { // в любом случае необходимо закрыть сокет и потоки
            System.out.println("Клиент был закрыт...");
        }


    }
}
