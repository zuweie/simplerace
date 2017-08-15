package co.bankoo.zuweie.simpleracing.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import co.bankoo.zuweie.simpleracing.R;

/**
 * Created by zuweie on 15/08/2017.
 */

public class Danger extends RaceObject {

    public Danger (Context context) {
        this.context = context;
        startAt = getFrameCounter();
        float type = (float) (Math.random() * 10);
        if (type >= 5) {
            cover = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bear);
        }else {
            cover = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tiger);
        }

        float pos = (float) (Math.random() * 10 );

        if (pos < 3) {
            this.setPosition(RaceView.POS_LEFT);
        }else if (pos > 3 && pos < 7) {
            this.setPosition(RaceView.POS_MIDDLE);
        }else{
            this.setPosition(RaceView.POS_RIGHT);
        }
    }

    @Override
    public void drawFrame(Canvas canvas, RectF viewPort) {

        endOfView = viewPort.bottom;
        beginOfView = viewPort.top;
        height = (viewPort.bottom - viewPort.top) * 0.1f;
        width  = height;
        // draw
        calCoordinate(viewPort);

        float left = mCoord.x - width / 2;
        float top  = mCoord.y - height / 2;
        float right = left + width;
        float bottom = top + height;

        RectF imgF = new RectF(left, top, right, bottom);

        canvas.drawBitmap(cover, null, imgF, null);

    }

    @Override
    public RectF getSafeRange() {
        safeRectf.set(mCoord.x - width/2, mCoord.y - height/2 , mCoord.x + width/2, mCoord.y + height/2);
        return safeRectf;
    }

    @Override
    public boolean isDead() {
        return mCoord.y >= endOfView;
    }

    @Override
    public boolean isVisual() {
        return false;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void calCoordinate (RectF viewPort) {

        float vw = viewPort.right - viewPort.left;
        float evw = vw * 0.9f;
        float ivw = vw * 0.1f;
        float x;
        if (position == RaceView.POS_LEFT) {
            x = viewPort.left + ivw + evw / 6;
        }else if (position == RaceView.POS_MIDDLE) {
            x = viewPort.left + (viewPort.right - viewPort.left) / 2;
        }else {
            x = viewPort.right - ivw - evw / 6;
        }

        float y = viewPort.top + (getFrameCounter() - startAt) * (height);

        setCoordinate(x, y);

    }
    float beginOfView;
    float endOfView;
    int startAt;
    int position;
    Context context;
    float height;
    float width;
    Bitmap cover;
    RectF safeRectf = new RectF();
}
