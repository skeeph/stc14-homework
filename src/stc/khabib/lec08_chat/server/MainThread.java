package stc.khabib.lec08_chat.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

class MainThread extends Thread {
    private Server server;

    public MainThread(Server s) {
        this.server = s;
    }

    @Override
    public void run() {
        System.out.println("Сервер запущен");
        while (!isInterrupted()) {
            try {
                Socket client = this.server.serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                String nick = in.readLine();
                System.out.println(nick);
                out.write("Добро пожаловать в чат: " + nick + "\n");
                out.flush(); // выталкиваем все из буфера
                client.setSoTimeout(500);
                ClientListener user = new ClientListener(client, nick, in, out);
                this.server.addUser(nick, user);
            } catch (SocketTimeoutException ignored) {

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Server shutdown");

    }
}