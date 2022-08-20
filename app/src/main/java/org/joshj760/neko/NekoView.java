package org.joshj760.neko;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

public class NekoView extends androidx.appcompat.widget.AppCompatImageView {

    @DrawableRes private final int nekoSpriteSheet = R.drawable.neko_sprite_sheet;


    public NekoView(Context context) {
        super(context);
        init();
    }

    public NekoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NekoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setImageDrawable(getContext().getDrawable(nekoSpriteSheet));
        
    }


}
