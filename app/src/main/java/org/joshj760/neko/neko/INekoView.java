package org.joshj760.neko.neko;

public interface INekoView {

    public NekoState getNekoState();

    public void setNekoState(NekoState nekoState);

    public float getX();

    public float getY();

    public void nextSpriteFrame();
    public void setPosition(int positionX, int positionY);

    /**
     * Get Width of this view
     * @return
     */
    public int getWidth();

    /**
     * Get Height of this view
     * @return
     */
    public int getHeight();

    public float getScale();
}
