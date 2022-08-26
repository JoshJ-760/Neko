package org.joshj760.neko;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.joshj760.neko.neko.NekoView;
import org.joshj760.neko.utility.BoundingBox;

public class MainActivity extends AppCompatActivity {

    ViewHolder viewHolder;
    NekoManager nekoManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewHolder = new ViewHolder();

        /**
         * In order to accurately measure the container, we must wait for the view to be
         * inflated. This block effectively does that. Wait to instantiate the runner.
         */
        ViewTreeObserver vto = viewHolder.container.getViewTreeObserver();
        if (vto.isAlive()) {
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    nekoManager = new NekoManager(viewHolder.nekoView,
                            new BoundingBox(0, viewHolder.container.getWidth(),
                            0, viewHolder.container.getHeight()));
                    nekoManager.start();

                    viewHolder.container.getViewTreeObserver()
                            .removeOnGlobalLayoutListener(this);
                }
            });
        }

        viewHolder.taskButton.setOnClickListener((v)->{
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            } else {
                Intent nekoServiceIntent = new Intent(this, NekoService.class);
                startForegroundService(nekoServiceIntent);
            }
        });

        viewHolder.container.setOnTouchListener((v, event) -> {
            if (nekoManager != null && event.getAction() == MotionEvent.ACTION_DOWN) {
                nekoManager.runTo((int)event.getX(), (int)event.getY());
            }

            return false;
        });

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        nekoManager = new NekoManager(viewHolder.nekoView,
                new BoundingBox(0, viewHolder.container.getWidth(),
                        0, viewHolder.container.getHeight()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (nekoManager != null) {
            nekoManager.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (nekoManager != null) {
            nekoManager.pause();
        }
    }

    class ViewHolder {
        Button taskButton;
        RelativeLayout container;
        NekoView nekoView;

        ViewHolder() {
            taskButton = findViewById(R.id.taskButton);
            container = findViewById(R.id.nekoContainer);
            nekoView = findViewById(R.id.nekoView);
        }
    }
}
