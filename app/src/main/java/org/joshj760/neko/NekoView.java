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

    @DrawableRes private static final int nekoSpriteSheet = R.drawable.neko_sprite_sheet;

    private float density;
    private int xPixelOffset = 0;
    private int yPixelOffset = 0;
    private float scale = 1;
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
        xPixelOffset = (int)(-1f * ((float)sprite.getColOffset())
                * ((float)SPRITE_WIDTH_PIXELS));
        yPixelOffset = (int)(-1f * ((float)sprite.getRowOffset())
                * ((float)SPRITE_HEIGHT_PIXELS));

        appliedMatrix.reset();
        appliedMatrix.postTranslate(xPixelOffset, yPixelOffset);
        appliedMatrix.postScale(this.scale, this.scale, 0, 0);
        setImageMatrix(appliedMatrix);
    }

    public void setScale(float scale) {
        this.scale = scale;

        appliedMatrix.reset();
        appliedMatrix.postTranslate(xPixelOffset, yPixelOffset);
        appliedMatrix.postScale(this.scale, this.scale, 0, 0);
        setImageMatrix(appliedMatrix);
        requestLayout();
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
        setPropertiesFromAttributes(attrs);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)((float)SPRITE_WIDTH_PIXELS*scale),
                (int)((float)SPRITE_HEIGHT_PIXELS*scale));
    }

    private void setPropertiesFromAttributes(@Nullable AttributeSet attrs) {
        scale = getScaleFromAttributes(attrs);
        currentVisualState = getInitialVisualState(attrs);
    }

    private float getScaleFromAttributes(@Nullable AttributeSet attrs) {
        //default scale is 1;
        float defValue = 1.0f;
        float scale = defValue;
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.NekoView,
                    0,
                    0);
            try {
                scale = a.getFloat(R.styleable.NekoView_nekoScale, defValue);
            } finally {
                a.recycle();
            }
        }

        return scale;
    }

    private NekoVisualState getInitialVisualState(@Nullable AttributeSet attrs) {
        //default state is "AWAKE"
        int defValue = NekoVisualState.AWAKE.ordinal();
        int initialVisualStateOrdinal = defValue;
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.NekoView,
                    0,
                    0);
            try {
                initialVisualStateOrdinal = a.getInt(R.styleable.NekoView_visualState, defValue);
            } finally {
                a.recycle();
            }
        }

        return NekoVisualState.fromOrdinal(initialVisualStateOrdinal);
    }


}
