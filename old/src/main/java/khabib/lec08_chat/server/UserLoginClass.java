package khabib.lec08_chat.server;

import java.io.*;
import java.net.Socket;

/**
 * Класс выполняющий авторизацию пользователя.
 * Авторизация заключается в получении от пользователя его имени, и его добавлении
 * в список авторизованных пользователей. Эта задача выполняется отдельным тред пулом
 */
public class UserLoginClass implements Runnable {
    Socket socket;
    Server server;

    /**
     * @param socket сокет-соединение с клиентом
     * @param server сервер чата
     */
    UserLoginClass(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Выполнение авторизации.
     */
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String nick = in.readLine();
            System.out.println(nick);
            out.write("Добро пожаловать в чат: " + nick + "\n");
            out.flush(); // выталкиваем все из буфера
            socket.setSoTimeout(500);
            ClientListener user = new ClientListener(socket, nick, in, out);
            this.server.addUser(nick, user);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
