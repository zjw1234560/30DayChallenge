package teamjoeys.dbms.umkc.challenge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

//this is the main menu for users that appears after they successfully log in.
//the menu displays a run goal and a push up goal along with the progress they have made
//the method update challenge is used to communicate with database


public class Challenge_Menu extends ActionBarActivity implements View.OnClickListener {

    private int count = 0;
    private ChallengeDatabase mDb;
    private Context mContext;

    public static int latestPushupGoalId = -1;
    public static int latestRunGoalId = -1;
    public static int personalPushupRecord = -1;
    public static double personalRunRecord = -1;
    public static int pushupGoalAmt = -1;
    public static double runGoalAmt = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge__menu);
        View spu_button = findViewById(R.id.start_pu);
        spu_button.setOnClickListener(this);
        View spr_button = findViewById(R.id.start_pacer);
        spr_button.setOnClickListener(this);

        mContext = this;
        mDb = new ChallengeDatabase(mContext);

        //this is where we would get challenge progress from database
        update_challenge();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challenge__menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_pu: {
                // If pushup goal not set, have user set goal

                Intent intent = new Intent(this, Pushup.class);
                startActivity(intent);
                break;
            }
            case R.id.start_pacer: {
                // If run goal not set, have user set goal

                Intent intent = new Intent(this, PacerRunActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void update_challenge() {
        TextView vValue = (TextView) findViewById(R.id.pushBestGoal);
        TextView tvValue = (TextView) findViewById(R.id.runBestGoal);
        String pushUpProgress=null;
        String runningProgress=null;
        //read in the data from database about which progress/goals made in challenge

        // Get latest pushup goal, store id
        int pushupGoalId = mDb.GetLatestPushupGoal(Login.UserId);
        if (pushupGoalId != -1)
        {
            latestPushupGoalId = pushupGoalId;
            pushupGoalAmt = mDb.GetPushupGoalAmt(latestPushupGoalId);

            // Get personal best from DB
            int pushupBest = mDb.GetBestPushupSession(Login.UserId, latestPushupGoalId);
            if (pushupBest != -1)
            {
                personalPushupRecord = pushupBest;
                if (personalPushupRecord >= pushupGoalAmt) {
                    vValue.setTextColor(Color.GREEN);
                }
                pushUpProgress = personalPushupRecord + "/" + pushupGoalAmt;
            }
        }
        // Get latest run goal, store id
        int runGoalId = mDb.GetLatestRunGoal(Login.UserId);
        if (runGoalId != -1)
        {
            latestRunGoalId = runGoalId;
            runGoalAmt = mDb.GetRunGoalAmt(latestRunGoalId);

            // Get personal run best from DB
            double runBest = mDb.GetBestRunSession(Login.UserId, latestRunGoalId);
            if (runBest != -1)
            {
                personalRunRecord = runBest;

                if (personalRunRecord >= runGoalAmt) {
                    tvValue.setTextColor(Color.GREEN);
                }
                runningProgress = personalRunRecord + "/" + runGoalAmt;
            }
        }

        vValue.setText(pushUpProgress);
        tvValue.setText(runningProgress);

    }

}
