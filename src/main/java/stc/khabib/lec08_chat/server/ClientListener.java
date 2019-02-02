package stc.khabib.lec08_chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

/**
 * Объект клиент чата
 */
public class ClientListener {
    private BufferedReader in;
    private BufferedWriter out;
    private Socket socket;
    private String userName;

    /**
     * @param socket   подключение к клиенту
     * @param userName имя клиента
     * @param in       входной поток данных сокета
     * @param out      выходной поток данных сокета
     */
    public ClientListener(Socket socket, String userName, BufferedReader in, BufferedWriter out) {
        this.socket = socket;
        this.userName = userName;
        this.in = in;
        this.out = out;
    }

    /**
     * @return Объект для чтения жанныъ из сокета клиента
     */
    public BufferedReader getReader() {
        return in;
    }

    /**
     * @return сокет подключения к клиенту
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * @return Имя клиента
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Отправить сообщение клиенту
     *
     * @param message текст сообщения
     * @throws IOException ошибка отправки
     */
    public void sendMessage(String message) throws IOException {
        out.write(message + "\n");
        out.flush();
    }

    /**
     * @return Строковое представление клиента
     */
    @Override
    public String toString() {
        return "ClientListener{" +
                "userName='" + userName + '\'' +
                ", socket=" + socket +
                '}';
    }
}
