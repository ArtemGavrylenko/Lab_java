package Chrono;

import java.util.concurrent.TimeUnit;

public class Clock implements Runnable {
    private int interval;

    public Clock(int interval) {
        this.interval = interval;
    }

    public void run() {
        try {
            while (true) {
                System.out.println("Clock Message: " + interval + " seconds");
                TimeUnit.SECONDS.sleep(interval);
            }
        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
        }
    }
}