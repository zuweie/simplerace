package co.bankoo.zuweie.simpleracing.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zuweie on 15/08/2017.
 */

public class RaceView extends View implements BeatController.BeatingListener{

    public RaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initRaceObjects();
        mBeating.setBeatingListener(this);
    }

    void initRaceObjects () {

        road = new Road();
        guy = new Guy(getContext());

    }

    @Override
    public void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        RectF viewPort = new RectF(0, 0, width, height);

        road.drawFrame(canvas, viewPort);
        guy.drawFrame(canvas, viewPort);
        for (RaceObject raceObject : Dangers) {
            raceObject.drawFrame(canvas, viewPort);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float width = getMeasuredWidth();
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            float tx = event.getX();

            if (tx < width / 3) {
                // left
                guy.setPosition(POS_LEFT);
            }else if (tx > width / 3 && tx <= 2 * (width / 3)) {
                // middle
                guy.setPosition(POS_MIDDLE);
            }else {
                // right
                guy.setPosition(POS_RIGHT);
            }
            postInvalidate();
        }
        return true;
    }

    @Override
    public void di() {
        RaceObject.frameGo();
        this.postInvalidate();

        // 投放 障碍物
        float putDanger = (float) (Math.random() * 100);
        if (putDanger > 90) {
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Dangers.add(new Danger(getContext()));
                }
            }, 100);
            //Dangers.add(new Danger(getContext()));
        }

    }

    @Override
    public void da() {
        // check dead object
        this.postDelayed(new Runnable() {
            @Override
            public void run() {

                // check

                Iterator<RaceObject> iterator = Dangers.iterator();
                while (iterator.hasNext()) {

                    RaceObject danger = iterator.next();

                    if (guy.testCollision(danger)){
                        //Toast.makeText(getContext(), "YOU GAME OVER!", Toast.LENGTH_SHORT).show();
                        gameOverListener.OnGameOver();
                        break;
                    }

                    if (danger.isDead()) {
                        iterator.remove();
                        getPointListner.OnGetPoint();
                    }
                }
            }
        }, 100);

    }

    public void start () {
        mBeating.start();
    }

    public void restart () {
        Dangers.clear();
        mBeating.setSpeed(5);
        mBeating.start();
    }

    public void stop () {
        mBeating.stop();
    }

    public void speedUp () {
        mBeating.setSpeed(mBeating.getSpeed()+1);
    }

    public void setGameOverListener (OnGameOverListener gameOverListener) {
        this.gameOverListener = gameOverListener;
    }

    public void setGetPointListner (OnGetPointListner getPointListner) {
        this.getPointListner = getPointListner;
    }

    public interface OnGameOverListener {
        public void OnGameOver();
    }

    public interface OnGetPointListner {
        public void OnGetPoint ();
    }

    OnGameOverListener gameOverListener;
    OnGetPointListner  getPointListner;

    public final static int POS_LEFT   = 1;
    public final static int POS_MIDDLE = 2;
    public final static int POS_RIGHT  = 3;

    //private List<RaceObject> mRaceObjects = new LinkedList();
    Guy guy;
    Road road;
    List<RaceObject> Dangers = new LinkedList<>();

    BeatController mBeating = new BeatController();
}
