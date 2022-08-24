package org.joshj760.neko;

import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import org.joshj760.neko.neko.Neko;
import org.joshj760.neko.neko.NekoView;
import org.joshj760.neko.utility.BoundingBox;

public class NekoService extends Service {

    LayoutInflater layoutInflater;
    ViewHolder viewHolder;

    Neko neko;

    @Override
    public void onCreate() {
        super.onCreate();

        // Services aren't themed like Activities. So we set it
        setTheme(R.style.Theme_Neko);
        layoutInflater = LayoutInflater.from(this);

        configureView();

        /**
         * In order to accurately measure the container, we must wait for the view to be
         * inflated. This block effectively does that. Wait to instantiate the runner.
         */
        ViewTreeObserver vto = viewHolder.container.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    neko = new Neko(viewHolder.nekoView,
                            new BoundingBox(0, viewHolder.container.getWidth(),
                                    0, viewHolder.container.getHeight()));
                    neko.start();

                    viewHolder.container.getViewTreeObserver()
                            .removeOnGlobalLayoutListener(this);
                }
            });
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //Service runs without binder
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void configureView() {
        ViewGroup nekoOverlay = (ViewGroup)layoutInflater.inflate(R.layout.service_overlay, null, false);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSPARENT);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(nekoOverlay, params);

        viewHolder = new ViewHolder(nekoOverlay);
    }



    class ViewHolder {
        ViewGroup container;
        NekoView nekoView;

        ViewHolder(ViewGroup container) {
            this.container = container;

            nekoView = (NekoView) findViewById(R.id.nekoView);
        }

        private View findViewById(@IdRes int idRes) {
            return container.findViewById(idRes);
        }
    }

}
