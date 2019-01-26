package stc.khabib.lec08_chat.server;

import java.io.*;
import java.net.Socket;

public class UserLoginClass implements Runnable {
    Socket socket;
    Server server;

    UserLoginClass(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

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
