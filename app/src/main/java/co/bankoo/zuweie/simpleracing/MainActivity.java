package co.bankoo.zuweie.simpleracing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import co.bankoo.zuweie.simpleracing.game.Danger;
import co.bankoo.zuweie.simpleracing.game.RaceView;

public class MainActivity extends AppCompatActivity implements RaceView.OnGameOverListener, RaceView.OnGetPointListner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        raceView = (RaceView) findViewById(R.id.race);
        raceView.setGameOverListener(this);
        raceView.setGetPointListner(this);
        start_btn = (Button) findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started == false) {
                    if (beDead){
                        raceView.restart();
                    }else {
                        raceView.start();
                    }
                    started = true;
                    start_btn.setText("STOP");
                    gameOver.setVisibility(View.GONE);
                }else{
                    raceView.stop();
                    started = false;
                    start_btn.setText("START");
                }
            }
        });
        point = (TextView) findViewById(R.id.point);
        gameOver = (TextView) findViewById(R.id.game_over);
    }

    @Override
    public void OnGameOver() {
        beDead = true;
        raceView.stop();
        started = false;
        gameOver.setVisibility(View.VISIBLE);
        start_btn.setText("RESTART");
        point_counter = 0;
        last_level_point = 0;
    }

    @Override
    public void OnGetPoint() {
        point_counter++;
        point.setText(String.format("Point : %d", point_counter));

        if (point_counter < 150 && point_counter - last_level_point > 10 ) {
            raceView.speedUp();
            last_level_point = point_counter;
        }

    }


    RaceView raceView;
    Button start_btn;
    boolean started = false;
    TextView gameOver;
    TextView point;
    int point_counter = 0;
    int last_level_point = 0;
    boolean beDead = false;
}
