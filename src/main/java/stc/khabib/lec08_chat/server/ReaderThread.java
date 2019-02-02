package stc.khabib.lec08_chat.server;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Поток обработки клиентов.
 * <p>
 * Берет пользователя из очереди, пытается получить от него сообщение.
 * Если сообщение получено - отправляет его другим пользователям.
 * Если не получено - добавляет сообщение обратно в очередь.
 * Если сообщение равно bye - удаляет пользователя с сервера
 */
public class ReaderThread extends Thread {
    Server server;

    /**
     * конструктор
     *
     * @param server Сервер
     */
    public ReaderThread(Server server) {
        this.server = server;
    }

    /**
     * Основная работа потока
     */
    @Override
    public void run() {
        System.out.println("Reader is run: " + this.getName());
        while (!isInterrupted()) {
            //Берет пользователя из очереди, пытается получить от него сообщение.
            ClientListener cl = this.server.usersListeners.poll();
            if (cl == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                    this.interrupt();
                }
                continue;
            }
            boolean addBack = true;
            try {
                String word = cl.getReader().readLine();
                if (word != null && !word.equals("bye")) {
                    //Если сообщение получено - отправляет его другим пользователям.
                    server.sendMessageFromUser(cl.getUserName(), word);
                    continue;
                }
                //Если сообщение равно bye - удаляет пользователя с сервераеу
                addBack = false;
                word = "User " + cl.getUserName() + " logged out";
                System.out.println(word);
                server.sendServerMessage(word);
                cl.getSocket().close();
                this.server.removeUser(cl.getUserName());

            } catch (SocketTimeoutException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (addBack) {
                    this.server.usersListeners.add(cl);
                } else {
                    System.out.println("Client don't back to queue: " + cl);
                }
            }
        }
        System.out.println("Reader stopped : " + this.getName());
    }
}
