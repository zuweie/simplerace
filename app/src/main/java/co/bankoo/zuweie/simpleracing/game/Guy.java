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

public class Guy extends RaceObject {

    public Guy (Context context) {
        this.context = context;
    }

    @Override
    public void drawFrame(Canvas canvas, RectF viewPort) {

        float vw = (viewPort.right - viewPort.left) * 0.9f;
        float gw = vw * 0.1f;
        float gh = gw;

        width = gw;
        height = gh;

        if (mCoord.x < 0.0f || mCoord.y < 0.0f) {
            // 初始化起位置
            setPosition(RaceView.POS_LEFT);
        }

        setCoordinateByPos(viewPort);
        float ileft = mCoord.x - gw / 2;
        float itop  = mCoord.y - gh / 2;
        float iright = ileft + gw;
        float ibottom = itop + gh;


        RectF imgF = new RectF(ileft, itop, iright, ibottom);

        if (jeep == null){
            jeep = BitmapFactory.decodeResource(context.getResources(), R.mipmap.jeep);
        }

        canvas.drawBitmap(jeep, null, imgF, null);

    }

    @Override
    public RectF getSafeRange() {
        safeRectf.set(mCoord.x - width / 2, mCoord.y - height / 2, mCoord.x + width / 2, mCoord.y + height / 2);
        return safeRectf;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public boolean isVisual() {
        return false;
    }

    public void setCoordinateByPos (RectF viewPort) {

        float vw = (viewPort.right - viewPort.left) * 0.9f;
        float lvw = (viewPort.right - viewPort.left) * 0.1f;
        float vvw = vw / 3;
        float gw = vw * 0.1f;
        float gh = gw;

        float y = viewPort.bottom - gh * 2;
        float x;

        if (this.position == RaceView.POS_LEFT){
            x = viewPort.left + lvw + vvw / 2;
        }else if (this.position == RaceView.POS_MIDDLE) {
            x = viewPort.left + (viewPort.right - viewPort.left) / 2;
        }else {
            x = viewPort.right - lvw - vvw / 2;
        }
        setCoordinate(x, y);
    }

    public void setPosition (int position) {
        this.position = position;
    }

    RectF viewPort;
    Bitmap jeep;
    Context context;
    int position;
    float width;
    float height;
    RectF safeRectf = new RectF();
}
