package stc.khabib.lec08_chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Сервер чата;
 */
public class Server {
    /**
     * Список пользователей, с их именами
     */
    private final Map<String, ClientListener> users;
    /**
     * Очередь пользователей для получения сообщеинй
     */
    public Queue<ClientListener> usersListeners;
    ServerSocket serverSocket;
    /**
     * Тред пул, выполняющий авторизаци пользователей
     */
    ExecutorService loginService;
    /**
     * Список потоков, слушающих пользователей
     */
    private List<Thread> readerThread;

    /**
     * Конструктор сервера
     *
     * @throws IOException Ошибка работы с сокетами
     */
    public Server() throws IOException {
        serverSocket = new ServerSocket(4004);
        serverSocket.setSoTimeout(1000);
        users = new ConcurrentHashMap<>();
        usersListeners = new LinkedBlockingQueue<>();
        readerThread = new LinkedList<>();
        loginService = Executors.newCachedThreadPool();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Server srv = new Server();
        Thread mainThread = new MainThread(srv);
        mainThread.start();
        mainThread.setName("Main thread");
        srv.startReaderThreads();

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String serverInput;
        while (!(serverInput = stdIn.readLine().trim()).equals("bye")) {
            System.out.println("Server printed: " + serverInput);
        }
        srv.sendServerMessage("Server is shutting down");
        srv.stopReaderThreads();
        mainThread.interrupt();
        mainThread.join();

    }

    /**
     * Запускает заданное число потоков, обрабатывающих сообщения от клиентов
     */
    private void startReaderThreads() {
        for (int i = 0; i < 10; i++) {
            Thread th = new ReaderThread(this);
            th.setName("Reader thread - " + i);
            th.start();
            this.readerThread.add(th);
        }
    }

    /**
     * Остановка потоков обработки клиентов
     *
     * @throws InterruptedException ошибка при остановке
     */
    private void stopReaderThreads() throws InterruptedException {
        for (Thread thread : this.readerThread) {
            thread.interrupt();
        }
        for (Thread thread : this.readerThread) {
            thread.join();
        }
    }

    /**
     * Добавление авторизованного пользователя на сервер
     *
     * @param name   имя пользователя
     * @param client пользователь
     */
    public void addUser(String name, ClientListener client) {
        this.users.put(name, client);
        this.usersListeners.add(client);
    }

    /**
     * Отправить сообщение от пользователя остальным пользователям.
     *
     * @param userName автор сообщения
     * @param message  текст сообщения
     * @throws IOException ошибка отправки сообщения
     */
    public void sendMessageFromUser(String userName, String message) throws IOException {
        message = String.format("<%s>: %s", userName, message);
        for (Map.Entry<String, ClientListener> user : users.entrySet()) {
            user.getValue().sendMessage(message);
        }
    }

    /**
     * Отправиль пользователям сообщение от сервера(
     *
     * @param message текст сообщения
     * @throws IOException ошибка отправки сообщения
     */
    public void sendServerMessage(String message) throws IOException {
        message = String.format("*** %s ***", message);
        for (Map.Entry<String, ClientListener> user : users.entrySet()) {
            user.getValue().sendMessage(message);
        }
    }

    /**
     * Удаление пользователя с сервера
     *
     * @param userName имя пользователя
     */
    public void removeUser(String userName) {
        this.users.remove(userName);
    }
}