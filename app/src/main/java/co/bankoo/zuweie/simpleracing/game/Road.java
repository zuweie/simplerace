package co.bankoo.zuweie.simpleracing.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by zuweie on 15/08/2017.
 */

public class Road extends RaceObject {
    @Override
    public void drawFrame(Canvas canvas, RectF viewPort) {

        float view_w = viewPort.right - viewPort.left;
        float view_h = viewPort.bottom - viewPort.top;

        float road_block_w = view_w * 0.06f;
        float road_block_h = road_block_w;
        int road_block_number = (int) (view_h / road_block_w);

        // draw the road on two side
        int j;
        if (getFrameCounter() %2 == 0) {
            j=0;
        }else{
            j=1;
        }

        RectF block_left = new RectF();
        RectF block_right = new RectF();

        for (int i=0; i<road_block_number; ++i) {


            if (i%2 == 0) {
                float bll = viewPort.left;
                float blt = viewPort.top+i*road_block_h;
                float blr = bll + road_block_w;
                float blb = blt + road_block_h;

                block_left.set(bll, blt, blr, blb);

                float brl = viewPort.right - road_block_w;
                float brt = viewPort.top+i*road_block_h;
                float brr = brl + road_block_w;
                float brb = brt + road_block_h;
                block_right.set(brl, brt, brr, brb);

                if (j++%2 == 0) {
                    mPaint.setColor(Color.BLUE);
                }else{
                    mPaint.setColor(Color.GREEN);
                }

                canvas.drawRect(block_left, mPaint);
                canvas.drawRect(block_right, mPaint);
            }
        }
    }

    @Override
    public RectF getSafeRange() {
        return null;
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public boolean isVisual() {
        return true;
    }
    Paint mPaint = new Paint();
}
