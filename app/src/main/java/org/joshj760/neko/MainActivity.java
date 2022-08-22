package org.joshj760.neko;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.joshj760.neko.neko.Neko;
import org.joshj760.neko.neko.NekoView;
import org.joshj760.neko.utility.BoundingBox;

public class MainActivity extends AppCompatActivity {

    ViewHolder viewHolder;
    Neko neko;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewHolder = new ViewHolder();
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
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        neko = new Neko(viewHolder.nekoView,
                new BoundingBox(0, viewHolder.container.getWidth(),
                        0, viewHolder.container.getHeight()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (neko != null) {
            //neko.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (neko != null) {
            neko.pause();
        }
    }

    class ViewHolder {
        RelativeLayout container;
        NekoView nekoView;

        ViewHolder() {
            container = findViewById(R.id.nekoContainer);
            nekoView = findViewById(R.id.nekoView);
        }
    }
}
