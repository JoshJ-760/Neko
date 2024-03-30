package org.joshj760.neko.sprite;

public enum NekoSprite {
    // Identify sprite locations on the sprite-sheet
    AWAKE(0,0),
    DOWN1(1, 0),
    DOWN2(0, 1),
    LEFT1(0, 3),
    LEFT2(1, 3),
    RIGHT1(4, 3),
    RIGHT2(4,2),
    UP1(5, 0),
    UP2(4, 4),
    CLAW_DOWN1(2, 0),
    CLAW_DOWN2(1, 1),
    CLAW_LEFT1(2, 3),
    CLAW_LEFT2(3, 3),
    CLAW_RIGHT1(0, 4),
    CLAW_RIGHT2(1, 4),
    CLAW_UP1(0, 5),
    CLAW_UP2(1, 5),
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
    WASH(3,0),
    IDLE(4, 0);

    private final int colOffset;
    private final int rowOffset;

    NekoSprite(int colOffset, int rowOffset) {
        this.colOffset = colOffset;
        this.rowOffset = rowOffset;
    }

    public int getColOffset() {
        return colOffset;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public static NekoSprite fromOrdinal(int value) {
        return NekoSprite.values()[value];
    }
}
