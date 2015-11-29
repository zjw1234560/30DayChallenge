package teamjoeys.dbms.umkc.challenge;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.os.SystemClock;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by dvgalarza on 11/23/15.
 */
public class PacerRunActivity extends ActionBarActivity implements View.OnClickListener {

    private double mDistanceRan;
    private Chronometer my_chronometer;
    private Handler mHandler;
    private int mInterval = 10000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacer_run);
        my_chronometer = (Chronometer) findViewById(R.id.chronometer);
        View S_button = findViewById(R.id.start_button);
        S_button.setOnClickListener(this);
        View F_button = findViewById(R.id.finish_button);
        F_button.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_button) {
            my_chronometer.setBase(SystemClock.elapsedRealtime());
            my_chronometer.start(); // start timer
            // Every 10 seconds, record new timestamp
            // distanceBetweenPoints = distance from prev_timestamp to current_timestamp
            // mDistanceRan += distanceBetweenPoints;

            // set time num label to current_timestamp time
            // set distance num label to mDistanceRan (make green if goal reached, blue otherwise)
            mHandler = new Handler();
            startRepeatingTask();

            }
        if (v.getId() == R.id.finish_button) {
                my_chronometer.stop();
                stopRepeatingTask();

            }


        }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "Distance Recorded", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(mStatusChecker, mInterval);
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }
}
