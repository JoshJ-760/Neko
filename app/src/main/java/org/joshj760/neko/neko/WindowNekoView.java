package org.joshj760.neko.neko;

import android.content.Context;
import android.view.WindowManager;

public class WindowNekoView extends NekoView{
    private final WindowManager windowManager;
    private final WindowManager.LayoutParams params;

    public WindowNekoView(Context context, WindowManager.LayoutParams params) {
        super(context);
        windowManager = context.getSystemService(WindowManager.class);
        this.params = params;
    }

    @Override
    public float getX() {
        return params.x;
    }

    @Override
    public float getY() {
        return params.y;
    }

    @Override
    public void setPosition(int positionX, int positionY) {
        post(()->{
            params.x = positionX;
            params.y = positionY;
            windowManager.updateViewLayout(this, params);
        });
    }
}
