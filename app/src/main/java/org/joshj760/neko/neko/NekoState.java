package org.joshj760.neko.neko;

import org.joshj760.neko.sprite.NekoSprite;

import java.util.Arrays;
import java.util.List;

public enum NekoState {
    AWAKE(NekoSprite.AWAKE),
    RUNNING_DOWN(NekoSprite.DOWN1, NekoSprite.DOWN2),
    RUNNING_LEFT(NekoSprite.LEFT1, NekoSprite.LEFT2),
    RUNNING_RIGHT(NekoSprite.RIGHT1, NekoSprite.RIGHT2),
    RUNNING_UP(NekoSprite.UP1, NekoSprite.UP2),
    CLAW_DOWN(NekoSprite.CLAW_DOWN1, NekoSprite.CLAW_DOWN2),
    CLAW_LEFT(NekoSprite.CLAW_LEFT1, NekoSprite.CLAW_LEFT2),
    CLAW_RIGHT(NekoSprite.CLAW_RIGHT1, NekoSprite.CLAW_RIGHT2),
    CLAW_UP(NekoSprite.CLAW_UP1, NekoSprite.CLAW_UP2),
    ASLEEP(NekoSprite.SLEEP1, NekoSprite.SLEEP1, NekoSprite.SLEEP2, NekoSprite.SLEEP2),
    RUNNING_UP_RIGHT(NekoSprite.UP_RIGHT1, NekoSprite.UP_RIGHT2),
    RUNNING_UP_LEFT(NekoSprite.UP_LEFT1, NekoSprite.UP_LEFT2),
    RUNNING_DOWN_RIGHT(NekoSprite.DOWN_RIGHT1, NekoSprite.DOWN_RIGHT2),
    RUNNING_DOWN_LEFT(NekoSprite.DOWN_LEFT1, NekoSprite.DOWN_LEFT2),
    SCRATCHING_SELF(NekoSprite.SCRATCH1, NekoSprite.SCRATCH2),
    YAWNING (NekoSprite.YAWN),
    WASH(NekoSprite.WASH, NekoSprite.IDLE),
    IDLE (NekoSprite.IDLE);

    List<NekoSprite> spriteList;

    NekoState(NekoSprite ... sprites) {
        spriteList = Arrays.asList(sprites);
    }

    public static NekoState fromOrdinal(int value) {
        return NekoState.values()[value];
    }

    public List<NekoSprite> getSpriteList() {
        return spriteList;
    }
}
