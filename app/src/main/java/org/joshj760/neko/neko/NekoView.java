package org.joshj760.neko.neko;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import org.apache.commons.collections4.iterators.LoopingListIterator;
import org.joshj760.neko.R;
import org.joshj760.neko.sprite.NekoSprite;
import org.joshj760.neko.sprite.NekoSpriteView;

import java.util.Iterator;

public class NekoView extends NekoSpriteView implements INekoView {

    private static final NekoState DEFAULT_NEKO_STATE = NekoState.AWAKE;

    private NekoState currentNekoState;
    private Iterator<NekoSprite> spriteIterator;

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

    public NekoState getNekoState() {
        return currentNekoState;
    }

    public void setNekoState(NekoState nekoState) {
        this.currentNekoState = nekoState;
        this.spriteIterator = new LoopingListIterator<>(nekoState.getSpriteList());
        nextSpriteFrame();
    }

    public void setPosition(int positionX, int positionY) {
        this.post(()-> {
            setX(positionX);
            setY(positionY);
        });
    }

    public void nextSpriteFrame() {
        NekoSprite nekoSprite = spriteIterator.next();
        this.post(()->{
            this.setSprite(nekoSprite); //this also invalidates the view and requests the redraw
        });
    }

    private void init(@Nullable AttributeSet attrs) {
        setPropertiesFromAttributes(attrs);
    }

    private void setPropertiesFromAttributes(@Nullable AttributeSet attrs) {
        setNekoState(getStateFromAttributes(attrs));
    }

    /**
     * Specifically looks for the nekoState attribute and returns its value, or returns
     * the default value DEFAULT_STATE if it isn't available
     * @param attrs
     */
    private NekoState getStateFromAttributes(@Nullable AttributeSet attrs) {
        int defValue = DEFAULT_NEKO_STATE.ordinal();
        int initialStateOrdinal = defValue;
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.NekoView,
                    0,
                    0);
            try {
                initialStateOrdinal = a.getInt(R.styleable.NekoView_nekoState,
                        defValue);
            } finally {
                a.recycle();
            }
        }

        return NekoState.fromOrdinal(initialStateOrdinal);
    }


}
