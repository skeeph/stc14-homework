package stc.khabib.lec08_chat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientListener extends Thread {
    BufferedReader in;
    BufferedWriter out;
    private Socket socket;
    private Server server;
    private String userName;

    public ClientListener(Socket socket, Server server, String userName, BufferedReader in, BufferedWriter out) {
        this.socket = socket;
        this.server = server;
        this.userName = userName;
        this.in = in;
        this.out = out;
    }

    public void sendMessage(String message) throws IOException {
        out.write(message + "\n");
        out.flush();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
//                Thread.sleep(1000);
                String word = in.readLine();
                if (word == null || word.equals("bye")) {
                    interrupt();
                    word = "User " + this.userName + " logged out";
                    System.out.println(word);
                    server.sendServerMessage(word);
                    this.socket.close();
                    this.server.removeUser(this.userName);
                    continue;
                }
                server.sendMessageFromUser(userName, word);
            } catch (SocketTimeoutException ignored) {
                System.out.println("client read timed out");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
