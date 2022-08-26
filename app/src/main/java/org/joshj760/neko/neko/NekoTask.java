package org.joshj760.neko.neko;

import android.util.Log;

import androidx.annotation.NonNull;

import org.joshj760.neko.utility.BoundingBox;
import org.joshj760.neko.utility.Utility;

import static org.joshj760.neko.neko.NekoState.ASLEEP;
import static org.joshj760.neko.neko.NekoState.AWAKE;
import static org.joshj760.neko.neko.NekoState.IDLE;
import static org.joshj760.neko.neko.NekoState.SCRATCHING_SELF;
import static org.joshj760.neko.neko.NekoState.WASH;
import static org.joshj760.neko.neko.NekoState.YAWNING;

public class NekoTask implements Runnable {
    private static final double ONE_16th_CIRCLE = 360d/16d;
    private static final double ONE_8th_CIRCLE = 360d/8d;
    private static final int NEKO_SPEED_UNSCALED = 12;    //pixels per second


    private final INekoView nekoView;

    private BoundingBox boundingBox;
    private NekoState nekoState;

    private int countDown;
    private int nekoSpeed;
    private int nekoX;
    private int nekoY;
    private int targetX;
    private int targetY;
    private int dX;
    private int dY;


    public NekoTask(INekoView nekoView, BoundingBox boundingBox) {
        this.nekoView = nekoView;
        this.boundingBox = boundingBox;

        init();
    }

    private void init() {
        setAwake();
        countDown = Timings.FRAMES_AWAKE;
        //Bounding box but keep Neko in view by restricting view from partially leaving box
        boundingBox = new BoundingBox(boundingBox.getX1(),
                boundingBox.getX2()-nekoView.getWidth(),
                boundingBox.getY1(),
                boundingBox.getY2()-nekoView.getHeight());
        nekoSpeed = (int)(NEKO_SPEED_UNSCALED * nekoView.getScale());
    }

    @Override
    //represents a single frame
    public void run() {
        try {
            Log.d("NekoTask", this.toString());

            switch (nekoState) {
                case AWAKE:
                    awakeFrame();
                    break;
                case RUNNING_UP:
                case RUNNING_DOWN:
                case RUNNING_LEFT:
                case RUNNING_RIGHT:
                case RUNNING_UP_LEFT:
                case RUNNING_UP_RIGHT:
                case RUNNING_DOWN_LEFT:
                case RUNNING_DOWN_RIGHT:
                    runFrame();
                    break;
                case IDLE:
                    idleFrame();
                    break;
                case WASH:
                    washFrame();
                    break;
                case ASLEEP:
                    sleepFrame();
                    break;
                case CLAW_UP:
                case CLAW_DOWN:
                case CLAW_LEFT:
                case CLAW_RIGHT:
                    clawFrame();
                    break;
                case YAWNING:
                    yawnFrame();
                    break;
                case SCRATCHING_SELF:
                    scratchFrame();
                    break;
            }
        } catch (Exception ex) {
            Log.e("NekoTask", ex.getMessage(), ex);
        }
    }

    @Override
    public String toString() {
        return "NekoTask{" +
                "countDown=" + countDown +
                ", nekoSpeed=" + nekoSpeed +
                ", nekoX=" + nekoX +
                ", nekoY=" + nekoY +
                ", targetX=" + targetX +
                ", targetY=" + targetY +
                ", dX=" + dX +
                ", dY=" + dY +
                ", neko.x=" + nekoView.getX() +
                ", neko.y=" + nekoView.getY() +
                ", boundingBox=" + boundingBox +
                '}';
    }

    private void scratchFrame() {
        nekoView.nextSpriteFrame();
        if (--countDown <= 0) {
            setYawn();
        }
    }

    private void yawnFrame() {
        nekoView.nextSpriteFrame();
        if (--countDown <= 0) {
            setSleep();
        }
    }

    private void clawFrame() {
    }

    private void sleepFrame() {
        nekoView.nextSpriteFrame();
        if (--countDown <= 0) {
            setAwake();
        }
    }

    private void washFrame() {
        nekoView.nextSpriteFrame();
        if (--countDown <= 0) {
            setScratch();
        }
    }

    private void idleFrame() {
        if (--countDown <= 0) {
            setWash();
        }
    }

    private void runFrame() {
        nekoView.nextSpriteFrame();

        nekoX += dX;
        nekoY += dY;

        nekoView.setPosition(nekoX, nekoY);

        if (--countDown <= 0) {
            setIdle();
        }
    }

    private void awakeFrame() {
        if (countDown-- <= 0) {
            //change to running
            runTo(Utility.randomXInBounds(boundingBox), Utility.randomYInBounds(boundingBox));
        } else {
            //do nothing
        }
    }

    private void setAwake() {
        setNekoState(AWAKE);
        countDown = Timings.FRAMES_AWAKE;
    }

    public void runTo(int targetX, int targetY) {
        nekoX = (int)nekoView.getX();
        nekoY = (int)nekoView.getY();
        this.targetX = targetX;
        this.targetY = targetY;

        //Assign a running directional sprite to Neko
        double angleRadians = Math.atan2(  nekoY - this.targetY, this.targetX - nekoX );

        double angleDegrees = angleRadians * ( 180 / Math.PI );
        angleDegrees = Utility.normalize(angleDegrees);
        assignRunStateByDegree(angleDegrees);

        nekoSpeed = (int)(NEKO_SPEED_UNSCALED * nekoView.getScale());

        dX = (int)(nekoSpeed * Math.cos(angleRadians));
        dY = -1*(int)(nekoSpeed * Math.sin(angleRadians));

        //set a countdown for number of frames to run for
        countDown = (int)(Math.abs((double)(targetX - nekoX)/(double)dX));
    }

    private void assignRunStateByDegree(double angleDegrees) {
        if (angleDegrees <= ONE_16th_CIRCLE) {
            setNekoState(NekoState.RUNNING_RIGHT);
        } else if (angleDegrees <= ONE_16th_CIRCLE + ONE_8th_CIRCLE) {
            setNekoState(NekoState.RUNNING_UP_RIGHT);
        } else if (angleDegrees <= ONE_16th_CIRCLE + 2*ONE_8th_CIRCLE) {
            setNekoState(NekoState.RUNNING_UP);
        } else if (angleDegrees <= ONE_16th_CIRCLE + 3*ONE_8th_CIRCLE) {
            setNekoState(NekoState.RUNNING_UP_LEFT);
        } else if (angleDegrees <= ONE_16th_CIRCLE + 4* ONE_8th_CIRCLE) {
            setNekoState(NekoState.RUNNING_LEFT);
        } else if (angleDegrees <= ONE_16th_CIRCLE + 5* ONE_8th_CIRCLE) {
            setNekoState(NekoState.RUNNING_DOWN_LEFT);
        } else if (angleDegrees <= ONE_16th_CIRCLE + 6* ONE_8th_CIRCLE) {
            setNekoState(NekoState.RUNNING_DOWN);
        } else if (angleDegrees <= ONE_16th_CIRCLE + 7* ONE_8th_CIRCLE) {
            setNekoState(NekoState.RUNNING_DOWN_RIGHT);
        } else {
            setNekoState(NekoState.RUNNING_RIGHT);
        }
    }

    private void setIdle() {
        setNekoState(IDLE);
        countDown = Timings.FRAMES_STOP;
    }

    private void setWash() {
        setNekoState(WASH);
        countDown = Timings.FRAMES_WASH;
    }

    private void setSleep() {
        setNekoState(ASLEEP);
        countDown = Utility.randomInt(60*5); //60 seconds
    }

    private void setScratch() {
        setNekoState(SCRATCHING_SELF);
        countDown = Timings.FRAMES_SCRATCH;
    }

    private void setYawn() {
        setNekoState(YAWNING);
        countDown = Timings.FRAMES_YAWN;
    }


    private void setNekoState(NekoState nekoState) {
        this.nekoState = nekoState;
        nekoView.setNekoState(nekoState);
    }

    private static class Timings {
        //These timings copied from Neko98 source code authored by David Harvey
        private static final int FRAMES_AWAKE = 3;
        private static final int FRAMES_YAWN = 3;
        private static final int FRAMES_SCRATCH = 4;
        private static final int FRAMES_CLAW = 10;
        private static final int FRAMES_WASH = 10;
        private static final int FRAMES_STOP = 4;
    }
}
