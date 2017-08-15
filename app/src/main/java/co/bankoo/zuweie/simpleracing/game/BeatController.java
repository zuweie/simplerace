package co.bankoo.zuweie.simpleracing.game;

/**
 * Created by zuweie on 15/08/2017.
 */

public class BeatController implements Runnable{

    @Override
    public void run() {
        while (mStarted) {

            beatingListener.di();
            beatingListener.da();

            try {
                Thread.sleep(1000 / mSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSpeed (int speed) {
        this.mSpeed = speed;
    }

    public int getSpeed () {
        return this.mSpeed;
    }
    public void start () {
        synchronized (mStarted) {
            mStarted = true;
        }

        new Thread(this).start();
    }

    public void stop () {
        synchronized (mStarted){
            mStarted = false;
        }
    }

    public static interface BeatingListener {
        public void di();
        public void da();
    }

    public void setBeatingListener (BeatingListener listener) {
        this.beatingListener = listener;
    }
    BeatingListener beatingListener;

    Boolean mStarted = false;
    int mSpeed = 5;
}
