package fr.noelproject.myapplication;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT_VALUE_TIMER = 10000;
    private static final int NUMBER_COLUMN_SERIES = 6;
    private static final int DEFAULT_NUMBER_SERIES = 4;

    private Chronometer chronometer;
    private boolean isStarted;
    private SeriesAdapter seriesAdapter;
    private long baseTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seriesAdapter = new SeriesAdapter(DEFAULT_NUMBER_SERIES);
        baseTimer = DEFAULT_VALUE_TIMER;
        chronometer = findViewById(R.id.chronometer);
        isStarted = false;
        final FrameLayout layoutChronometer = findViewById(R.id.layout_chronometer);
        chronometer.setBase(getBaseTime());
        chronometer.setCountDown(true);

        layoutChronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(getBaseTime());
                if (isStarted) {
                    stopChrono();
                    seriesAdapter.nextSerie();
                } else {
                    startChrono();
                }
            }
        });

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long heureDeDepart=chronometer.getBase();
                long heureActuelle=SystemClock.elapsedRealtime();
                if ((heureDeDepart-heureActuelle)<=0) {
                    chronometer.setBase(getBaseTime());
                    stopChrono();
                    seriesAdapter.nextSerie();
                }
            }
        });

        final RecyclerView recyclerView = findViewById(R.id.serie_view);
        GridLayoutManager gm = new GridLayoutManager(this, Math.min(NUMBER_COLUMN_SERIES, DEFAULT_NUMBER_SERIES));
        recyclerView.setLayoutManager(gm);
        recyclerView.setAdapter(seriesAdapter);
    }

    private void startChrono(){
        isStarted = true;
        seriesAdapter.setRestingTime(true);
        chronometer.start();
    }

    private void stopChrono(){
        isStarted = false;
        seriesAdapter.setRestingTime(false);
        chronometer.stop();
    }

    private long getBaseTime(){
        return ((SystemClock.elapsedRealtime()/1000)*1000)+1000+baseTimer;
    }

}
