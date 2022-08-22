package org.joshj760.neko.sprite;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import org.joshj760.neko.R;

public class NekoSpriteView extends androidx.appcompat.widget.AppCompatImageView {

    private static final int SPRITE_WIDTH_PIXELS = 32;
    private static final int SPRITE_HEIGHT_PIXELS = 32;
    private static final float DEFAULT_SCALE = 1;
    private static final NekoSprite DEFAULT_NEKO_SPRITE = NekoSprite.AWAKE;

    @DrawableRes private static final int nekoSpriteSheet = R.drawable.neko_sprite_sheet;

    private int xPixelOffset = 0;
    private int yPixelOffset = 0;
    private float scale = 1;
    private NekoSprite displayedSprite;
    private Matrix appliedMatrix = new Matrix();

    public NekoSpriteView(Context context) {
        super(context);
        init(null);
    }

    public NekoSpriteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NekoSpriteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * Sets the displayed sprite in this view
     * @param sprite the NekoSprite state to display
     */
    public void setSprite(NekoSprite sprite) {
        displayedSprite = sprite;
        xPixelOffset = (int)(-1f * ((float)sprite.getColOffset())
                * ((float)SPRITE_WIDTH_PIXELS));
        yPixelOffset = (int)(-1f * ((float)sprite.getRowOffset())
                * ((float)SPRITE_HEIGHT_PIXELS));

        invalidate();
    }

    /**
     * Sets the scale of this view
     * @param scale
     */
    public void setScale(float scale) {
        this.scale = scale;
        requestLayout();
    }

    /**
     * Applies the scaling/translation matrix on redraw
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        applyMatrix();
        canvas.concat(appliedMatrix);
        getDrawable().draw(canvas);
    }

    /**
     * regenerates the matrix based upon the latest translate(for sprite-offset)/scale data
     */
    private void applyMatrix() {
        appliedMatrix.reset();
        appliedMatrix.postTranslate(xPixelOffset, yPixelOffset);
        appliedMatrix.postScale(scale, scale, 0, 0);
    }

    private void init(@Nullable AttributeSet attrs) {
        //set image resource
        setImageResource(R.drawable.neko_sprite_sheet);

        //this allows scaling without smoothing the pixel art
        getDrawable().setFilterBitmap(false);

        //this allows sprite manipulation and scaling within bounds of image
        setImageMatrix(appliedMatrix);
        setScaleType(ScaleType.MATRIX);

        //get visual state if defined in attributes (or get default)
        setPropertiesFromAttributes(attrs);
    }

    /**
     * The view should be (sprite_size * scale) x (sprite_size * scale) large
     * @param widthMeasureSpec ignored
     * @param heightMeasureSpec ignored
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)((float)SPRITE_WIDTH_PIXELS*scale),
                (int)((float)SPRITE_HEIGHT_PIXELS*scale));
    }

    /**
     * Called on init to grab custom attributes and assign them to this view
     * @param attrs custom attributes to set
     */
    private void setPropertiesFromAttributes(@Nullable AttributeSet attrs) {
        scale = getScaleFromAttributes(attrs);
        setSprite(getInitialSprite(attrs));
    }

    /**
     * Specifically looks for the nekoScale attribute and returns its value, or returns
     * the default value DEFAULT_SCALE if it isn't available
     * @param attrs
     * @return
     */
    private float getScaleFromAttributes(@Nullable AttributeSet attrs) {
        float defValue = DEFAULT_SCALE;
        float scale = defValue;
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.NekoSpriteView,
                    0,
                    0);
            try {
                scale = a.getFloat(R.styleable.NekoSpriteView_nekoScale, defValue);
            } finally {
                a.recycle();
            }
        }

        return scale;
    }

    /**
     * Specifically looks for the NekoSprite attribute and returns its value, or returns
     * the default value DEFAULT_NEKO_SPRITE if it isn't available
     * @param attrs
     * @return
     */
    private NekoSprite getInitialSprite(@Nullable AttributeSet attrs) {
        int defValue = DEFAULT_NEKO_SPRITE.ordinal();
        int initialSpriteOrdinal = defValue;
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.NekoSpriteView,
                    0,
                    0);
            try {
                initialSpriteOrdinal = a.getInt(R.styleable.NekoSpriteView_nekoSprite,
                        defValue);
            } finally {
                a.recycle();
            }
        }

        return NekoSprite.fromOrdinal(initialSpriteOrdinal);
    }

    public float getScale() {
        return scale;
    }

    public NekoSprite getDisplayedSprite() {
        return displayedSprite;
    }
}
