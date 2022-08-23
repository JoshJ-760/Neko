package org.joshj760.neko.neko;

import android.util.Log;

import org.joshj760.neko.utility.BoundingBox;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Neko {

    private static final int DEFAULT_TIME_BETWEEN_UPDATES_MS = 200;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final NekoTask nekoTask;

    private ScheduledFuture<?> scheduledFuture;
    private int timeBetweenUpdatesMs = DEFAULT_TIME_BETWEEN_UPDATES_MS;

    public Neko(INekoView nekoView, BoundingBox boundingBox) {
        nekoTask = new NekoTask(nekoView, boundingBox);
    }

    public void setTimeBetweenUpdatesMs(int timeBetweenUpdatesMs) {
        this.timeBetweenUpdatesMs = timeBetweenUpdatesMs;
        pause();
        start();
    }

    public void start() {
        if (scheduledFuture != null) {
            //task already running
            Log.e(Neko.class.getSimpleName(), "start called, but task already running");
        }

        scheduledFuture = executor.scheduleAtFixedRate(nekoTask,
                0,
                timeBetweenUpdatesMs,
                TimeUnit.MILLISECONDS);
    }

    public void pause() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            scheduledFuture = null;
        }
    }


    public void runTo(int x, int y) {
        nekoTask.runTo(x, y);
    }
}
