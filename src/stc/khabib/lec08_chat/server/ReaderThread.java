package stc.khabib.lec08_chat.server;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class ReaderThread extends Thread {
    Server server;

    public ReaderThread(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        System.out.println("Reader is run: " + this.getName());
        while (!isInterrupted()) {
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
                    server.sendMessageFromUser(cl.getUserName(), word);
                    continue;
                }
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
