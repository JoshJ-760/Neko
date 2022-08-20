package org.joshj760.neko;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

public class NekoView extends androidx.appcompat.widget.AppCompatImageView {

    private static final int SPRITE_WIDTH_PIXELS = 32;
    private static final int SPRITE_HEIGHT_PIXELS = 32;
    @DrawableRes private static final int nekoSpriteSheet = R.drawable.neko_sprite_sheet;

    public enum VisualState {
        AWAKE(0,0),
        DOWN1(1, 0),
        DOWN2(0, 1),
        LEFT1(0, 3),
        LEFT2(1, 3),
        RIGHT1(4, 3),
        RIGHT2(4,2),
        UP1(5, 0),
        UP2(4, 4),
        SCRATCH_DOWN1(2, 0),
        SCRATCH_DOWN2(1, 1),
        SCRATCH_LEFT1(2, 3),
        SCRATCH_LEFT2(3, 3),
        SCRATCH_RIGHT1(0, 4),
        SCRATCH_RIGHT2(1, 4),
        SCRATCH_UP1(0, 5),
        SCRATCH_UP2(1, 5),
        SLEEP1(2, 4),
        SLEEP2(3, 4),
        UP_RIGHT1(5, 4),
        UP_RIGHT2(5, 3),
        UP_LEFT1(5, 2),
        UP_LEFT2(5, 1),
        DOWN_RIGHT1(1, 2),
        DOWN_RIGHT2(2, 2),
        DOWN_LEFT1(2, 1),
        DOWN_LEFT2(0, 2),
        SCRATCH1(3, 1),
        SCRATCH2(3, 2),
        YAWN(4, 1),
        LICK(3,0),
        STILL(4, 0)





        SCRATCH_DOWN_0(2, 0);


        ASLEEP_1(), ASLEEP_2;


        private final int colOffset;
        private final int rowOffset;

        VisualState(int colOffset, int rowOffset) {
            this.colOffset = colOffset;
            this.rowOffset = rowOffset;
        }
    }



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
        //set image resource
        setImageResource(R.drawable.neko_sprite_sheet);

        //this allows scaling without smoothing the pixel art
        getDrawable().setFilterBitmap(false);
    }


}
