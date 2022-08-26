package org.joshj760.neko;

import android.util.Log;

import org.joshj760.neko.neko.INekoView;
import org.joshj760.neko.neko.NekoTask;
import org.joshj760.neko.utility.BoundingBox;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NekoManager {
    private static final String TAG = "NekoManager";
    private static final int DEFAULT_TIME_BETWEEN_UPDATES_MS = 200;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final NekoTask nekoTask;

    private ScheduledFuture<?> scheduledFuture;
    private int timeBetweenUpdatesMs = DEFAULT_TIME_BETWEEN_UPDATES_MS;

    public NekoManager(INekoView nekoView, BoundingBox boundingBox) {
        nekoTask = new NekoTask(nekoView, boundingBox);
        Log.d(TAG, "Create Service" );
    }

    public void setTimeBetweenUpdatesMs(int timeBetweenUpdatesMs) {
        this.timeBetweenUpdatesMs = timeBetweenUpdatesMs;
        pause();
        start();
    }

    public void start() {
        if (scheduledFuture != null) {
            //task already running
            Log.e(NekoManager.class.getSimpleName(), "start called, but task already running");
            return;
        }

        scheduledFuture = executor.scheduleAtFixedRate(nekoTask,
                0,
                timeBetweenUpdatesMs,
                TimeUnit.MILLISECONDS);
        Log.d(TAG, "Start Service");
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
