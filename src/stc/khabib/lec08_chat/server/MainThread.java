package stc.khabib.lec08_chat.server;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Главный поток сервера, который ловит подключения пользователей
 */
class MainThread extends Thread {
    private Server server;

    /**
     * @param s Сервер
     */
    public MainThread(Server s) {
        this.server = s;
    }

    /**
     * Задача выполняемая в основном потоке
     */
    @Override
    public void run() {
        System.out.println("Сервер запущен");
        while (!isInterrupted()) {
            Socket client;
            try {
                // Получение подключения к клиенту
                client = this.server.serverSocket.accept();
                // Запуск задачи авторизации
                server.loginService.submit(new UserLoginClass(client, server));
            } catch (SocketTimeoutException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Server shutdown");
    }
}