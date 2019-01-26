package stc.khabib.lec08_chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final Map<String, ClientListener> users;
    ServerSocket serverSocket;
    ExecutorService es;

    public Server() throws IOException {
        serverSocket = new ServerSocket(4004);
        serverSocket.setSoTimeout(1000);
        es = Executors.newCachedThreadPool();
        users = new ConcurrentHashMap<>();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Server srv = new Server();
        Thread mainThread = new MainThread(srv);
        mainThread.start();
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String serverInput;
        while (!(serverInput = stdIn.readLine().trim()).equals("bye")) {
            System.out.println("Server printed: " + serverInput);
        }
        mainThread.interrupt();
        mainThread.join();
    }

    public void addUser(String name, ClientListener client) {
        this.users.put(name, client);
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
//                client.setSoTimeout(1000);
                ClientListener user = new ClientListener(client, server, nick, in, out);
                user.start();
                this.server.addUser(nick, user);
            } catch (SocketTimeoutException ignored) {

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Server shutdown");

    }
}