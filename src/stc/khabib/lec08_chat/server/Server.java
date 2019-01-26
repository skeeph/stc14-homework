package stc.khabib.lec08_chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private final Map<String, ClientListener> users;
    public Queue<ClientListener> usersListeners;
    ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(4004);
        serverSocket.setSoTimeout(1000);
        users = new ConcurrentHashMap<>();
        usersListeners = new LinkedBlockingQueue<>();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Server srv = new Server();
        Thread mainThread = new MainThread(srv);
        mainThread.start();
        mainThread.setName("Main thread");
        Thread reder = new ReaderThread(srv);
        reder.start();
        reder.setName("Reader thread 1");

        Thread reder2 = new ReaderThread(srv);
        reder2.start();
        reder2.setName("Reader thread 2");

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String serverInput;
        while (!(serverInput = stdIn.readLine().trim()).equals("bye")) {
            System.out.println("Server printed: " + serverInput);
        }
        mainThread.interrupt();
        mainThread.join();

        reder.interrupt();
        reder.join();

        reder2.interrupt();
        reder2.join();
    }

    public void addUser(String name, ClientListener client) {
        this.users.put(name, client);
        this.usersListeners.add(client);
    }

    public void sendMessageFromUser(String userName, String message) throws IOException {
        message = String.format("<%s>: %s", userName, message);
        for (Map.Entry<String, ClientListener> user : users.entrySet()) {
            user.getValue().sendMessage(message);
        }
    }

    public void sendServerMessage(String message) throws IOException {
        message = String.format("*** %s ***", message);
        for (Map.Entry<String, ClientListener> user : users.entrySet()) {
            user.getValue().sendMessage(message);
        }
    }

    public void removeUser(String userName) {
        this.users.remove(userName);
    }
}