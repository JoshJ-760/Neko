package org.joshj760.neko;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import org.joshj760.neko.neko.INekoView;
import org.joshj760.neko.neko.NekoView;
import org.joshj760.neko.neko.WindowNekoView;
import org.joshj760.neko.utility.BoundingBox;

import java.io.PipedInputStream;

public class NekoService extends Service {

    public static final String CHANNEL_ID = "channel123";
    public static final String CHANNEL_NAME = "my notification";
    public static final String CHANNEL_DESCRIPTION = "Test";

    WindowManager windowManager;
    ViewHolder viewHolder;

    NekoManager nekoManager;

    @Override
    public void onCreate() {
        super.onCreate();

        // Services aren't themed like Activities. So we set it
        setTheme(R.style.Theme_Neko);
        windowManager = NekoService.this.getSystemService(WindowManager.class);

        configureView();

        /**
         * In order to accurately measure the container, we must wait for the view to be
         * inflated. This block effectively does that. Wait to instantiate the runner.
         */
        ViewTreeObserver vto = viewHolder.nekoView.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                    int screenHeight = displayMetrics.heightPixels;
                    int screenWidth = displayMetrics.widthPixels;

                    nekoManager = new NekoManager(viewHolder.nekoView,
                            new BoundingBox(0, screenWidth,
                                    0, screenHeight));
                    nekoManager.start();

                    viewHolder.nekoView.getViewTreeObserver()
                            .removeOnGlobalLayoutListener(this);
                }
            });
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE);

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription(CHANNEL_DESCRIPTION);

        Notification notification =
                new Notification.Builder(this, CHANNEL_ID)
                        .setContentTitle("Neko")
                        .setContentText("Running")
                        .setContentIntent(pendingIntent)
                        .build();

// Notification ID cannot be 0.
        NotificationManager manager = this.getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
        startForeground(599, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("NekoService", "onDestroy");
    }

    //Service runs without binder
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void configureView() {
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSPARENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;

        WindowNekoView nekoOverlay = new WindowNekoView(this, params);
        nekoOverlay.setScale(4);
        wm.addView(nekoOverlay, params);

        viewHolder = new ViewHolder(nekoOverlay);
    }



    class ViewHolder {
        NekoView nekoView;

        @SuppressLint("ClickableViewAccessibility")
        ViewHolder(NekoView nekoView) {
            this.nekoView = nekoView;

            nekoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        Toast.makeText(NekoService.this, "Meow", Toast.LENGTH_SHORT).show();
//                        return true;
//                    }

                    if ((event.getAction() == MotionEvent.ACTION_OUTSIDE)
                            && (int)event.getY() != 0 && (int)event.getX() != 0) {
                        nekoManager.runTo((int)event.getRawX(), (int)event.getRawY());
                    }

                    return false;
                }
            });
        }
    }

}
