package de.hdmstuttgart.java_learn_app.database;

import java.util.concurrent.Executor;

public class ThreadExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }

}
