package stc.khabib.lec08_chat.client;

import java.io.*;
import java.net.Socket;

public class Client {
    private static Socket clientSocket; //сокет для общения
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?
    private static BufferedReader socketIn; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args) throws IOException {

        try {
            clientSocket = new Socket("localhost", 4004);
            clientSocket.setSoTimeout(1000);

            //  у сервера доступ на соединение
            reader = new BufferedReader(new InputStreamReader(System.in));
            // читать соообщения с сервера
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // писать туда же
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

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
//            clientSocket.close();
            System.out.println("You typed `bye`, shutting down");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally { // в любом случае необходимо закрыть сокет и потоки
            System.out.println("Клиент был закрыт...");
            socketIn.close();
            out.close();
            clientSocket.close();
        }


    }
}
