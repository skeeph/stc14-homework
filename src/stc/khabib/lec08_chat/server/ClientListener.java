package stc.khabib.lec08_chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class ClientListener {
    private BufferedReader in;
    private BufferedWriter out;
    private Socket socket;
    private String userName;

    public ClientListener(Socket socket, String userName, BufferedReader in, BufferedWriter out) {
        this.socket = socket;
        this.userName = userName;
        this.in = in;
        this.out = out;
    }

    public BufferedReader getReader() {
        return in;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUserName() {
        return userName;
    }

    public void sendMessage(String message) throws IOException {
        out.write(message + "\n");
        out.flush();
    }

    @Override
    public String toString() {
        return "ClientListener{" +
                "userName='" + userName + '\'' +
                ", socket=" + socket +
                '}';
    }
}
