package HCP.Monitors;

import java.util.concurrent.locks.ReentrantLock;

public class Logger {
    private final ReentrantLock monitor = new ReentrantLock();

    public Logger() {

    }
}
