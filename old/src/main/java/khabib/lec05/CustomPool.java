package khabib.lec05;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Простой кастомный тред пул
 */
public class CustomPool {
    private BlockingQueue<Runnable> tasks;
    private List<Thread> threads;
    private boolean isThreadPoolShutDownInitiated;

    /**
     * @param numberOfThreads число потоков в пуле
     */
    public CustomPool(int numberOfThreads) {
        this.tasks = new LinkedBlockingDeque<>();
        this.threads = new ArrayList<>(numberOfThreads);
        this.isThreadPoolShutDownInitiated = false;

        for (int i = 0; i < numberOfThreads; i++) {
            Worker w = new Worker(this);
            w.setName("Worker " + i);
            w.start();
            threads.add(w);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " is executing task.");
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        CustomPool p = new CustomPool(5);


        for (int i = 0; i < 100; i++) {
            p.submit(r);
        }

        p.shutdown();
    }

    /**
     * Добавляет задачу в очередь пула
     *
     * @param r задача на выполнение
     * @throws InterruptedException остановка задач
     */
    public void submit(Runnable r) throws InterruptedException {
        if (!this.isThreadPoolShutDownInitiated) {
            tasks.put(r);
        } else {
            throw new InterruptedException("Thread Pool shutdown is initiated, unable to submit task");
        }
    }

    /**
     * Выключение тред пула. Пул не принимает новые задачи, но те, что уже есть в очереди
     */
    public void shutdown() {
        isThreadPoolShutDownInitiated = true;
    }

    /**
     * Воркер - тред выполняющий задачи из очереди
     */
    private class Worker extends Thread {
        private CustomPool threadPool;

        /**
         * Конструктор, привязывает поток к пулу.
         *
         * @param threadPool тред пул
         */
        public Worker(CustomPool threadPool) {
            this.threadPool = threadPool;
        }


        /**
         * Пока пул не выключен, получает задачи из очереди и выполняет их.
         */
        @Override
        public void run() {
            while (!threadPool.isThreadPoolShutDownInitiated || !this.threadPool.tasks.isEmpty()) {
                Runnable r;
                while ((r = this.threadPool.tasks.poll()) != null) {
                    r.run();
                }
                try {
                    //TODO 02.02.19 skeeph: wait-notify
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
