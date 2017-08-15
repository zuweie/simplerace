package co.bankoo.zuweie.simpleracing.game;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by zuweie on 15/08/2017.
 */

public abstract class RaceObject {


    public abstract void drawFrame(Canvas canvas, RectF viewPort);

    public static void frameGo () {
        synchronized (frameCounter) {
            frameCounter++;
        }
    }
    public static int getFrameCounter () {
        synchronized (frameCounter) {
            return frameCounter;
        }
    }

    public void setCoordinate(float x, float y) {
        mCoord.x = x;
        mCoord.y = y;
    }

    public boolean testCollision (RaceObject other) {

        RectF mineRange = getSafeRange();
        RectF otherRange = other.getSafeRange();
        if (mineRange != null && otherRange != null) {
            return ((mineRange.left > otherRange.left && mineRange.left < otherRange.right)
                    || (mineRange.right > otherRange.left && mineRange.right < otherRange.right))
                    && ((mineRange.top > otherRange.top && mineRange.top < otherRange.bottom)
                    || (mineRange.bottom > otherRange.top && mineRange.bottom < otherRange.bottom));
        }else {
            return false;
        }
    }

    public abstract RectF getSafeRange ();

    public abstract boolean isDead();

    public abstract boolean isVisual();

    static private Integer frameCounter = 0;

    PointF mCoord = new PointF(-1, -1);
}
