package stc.khabib.lec08_chat.server;

import java.io.IOException;
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
            Socket client;
            try {
                client = this.server.serverSocket.accept();
                server.loginService.submit(new UserLoginClass(client, server));
            } catch (SocketTimeoutException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Server shutdown");
    }
}