package Chrono;

import java.util.concurrent.TimeUnit;

public class Chronometer implements Runnable {
    private long startTime;

    public Chronometer() {
        startTime = System.currentTimeMillis();
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public long getTime() {
        return System.currentTimeMillis() - startTime;
    }

    public void run() {
        try {
            while (true) {
                long elapsedTime = getTime();
                System.out.println("Elapsed Time: " + TimeUnit.MILLISECONDS.toSeconds(elapsedTime) + " seconds");

                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }
    }
}