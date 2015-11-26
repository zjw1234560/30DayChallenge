package teamjoeys.dbms.umkc.challenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.os.SystemClock;


/**
 * Created by dvgalarza on 11/23/15.
 */
public class PacerRunActivity extends ActionBarActivity implements View.OnClickListener {

    private Chronometer my_chronometer;
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
            my_chronometer.start();
        }
        if (v.getId() == R.id.finish_button){
            my_chronometer.stop();
        }

    }
}
