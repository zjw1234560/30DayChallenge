package teamjoeys.dbms.umkc.challenge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

//this is the push up feature
//it displays the push up goal and the count for the current session


public class Pushup extends ActionBarActivity implements View.OnClickListener {

    private int count = 0;
    private ChallengeDatabase mDb;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup);
        View L_button = findViewById(R.id.pushup_button);
        L_button.setOnClickListener(this);
        View F_button = findViewById(R.id.finish_button);
        F_button.setOnClickListener(this);

        mContext = this;
        mDb = new ChallengeDatabase(mContext);

        update_count();
        update_goal();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pushup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Pushup_tutorial.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pushup_button)
        {
            count+=1;
            update_count();
        }
        if (v.getId() == R.id.finish_button)
        {
            //send info for push up session to database

            mDb.AddPushupSession(Challenge_Menu.latestPushupGoalId, count);
            Intent intent = new Intent(this, Challenge_Menu.class);
            startActivity(intent);
        }
    }


    private void update_count(){
        TextView t = (TextView) findViewById(R.id.pu_counter);
        t.setText(Integer.toString(count));
    }

    private void update_goal(){
        TextView t = (TextView) findViewById(R.id.pu_goal);
        int currentGoalAmt = Challenge_Menu.pushupGoalAmt;
        t.setText(Integer.toString(currentGoalAmt));
    }
}




