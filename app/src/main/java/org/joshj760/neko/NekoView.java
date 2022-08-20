package org.joshj760.neko;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

public class NekoView extends androidx.appcompat.widget.AppCompatImageView {

    private static final int SPRITE_WIDTH_PIXELS = 32;
    private static final int SPRITE_HEIGHT_PIXELS = 32;
    private static float scale = 1;
    @DrawableRes private static final int nekoSpriteSheet = R.drawable.neko_sprite_sheet;

    private float density;
    private NekoVisualState currentVisualState;
    private Matrix appliedMatrix;

    public NekoView(Context context) {
        super(context);
        init(null);
    }

    public NekoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NekoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setSprite(NekoVisualState sprite) {
        float xPixelOffset = -1f * ((float)sprite.getColOffset()) * density
                * ((float)SPRITE_WIDTH_PIXELS) * scale;
        float yPixelOffset = -1f * ((float)sprite.getRowOffset()) * density
                * ((float)SPRITE_HEIGHT_PIXELS) * scale;

        appliedMatrix.setTranslate(xPixelOffset, yPixelOffset);
        setImageMatrix(appliedMatrix);
    }

    private void init(@Nullable AttributeSet attrs) {
        //get parameters of display for image size calculations
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        density = metrics.density;
        //TODO get width from attrs....
        //scale = (((float)getWidth()) / density / ((float)SPRITE_WIDTH_PIXELS));
        scale = 1;

        //set image resource
        setImageResource(R.drawable.neko_sprite_sheet);

        //this allows scaling without smoothing the pixel art
        getDrawable().setFilterBitmap(false);

        //this allows sprite manipulation and scaling within bounds of image
        appliedMatrix = new Matrix();
        setImageMatrix(appliedMatrix);
        setScaleType(ScaleType.MATRIX);

        //get visual state if defined in attributes (or get default)
        currentVisualState = getInitialVisualState(attrs);
    }


    private NekoVisualState getInitialVisualState(@Nullable AttributeSet attrs) {
        //set the state based on the xml (if exists)
        int initialVisualStateOrdinal = 0;
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.NekoView,
                    0,
                    0);
            try {
                initialVisualStateOrdinal = a.getInt(R.styleable.NekoView_visualState, 0);
            } finally {
                a.recycle();
            }
        }

        return NekoVisualState.fromOrdinal(initialVisualStateOrdinal);
    }





}
