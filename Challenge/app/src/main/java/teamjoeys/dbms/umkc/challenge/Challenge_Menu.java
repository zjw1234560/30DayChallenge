package teamjoeys.dbms.umkc.challenge;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import teamjoeys.dbms.umkc.challenge.ChallengeDatabase;

//this is the main menu for users that appears after they successfully log in.
//the menu displays a run goal and a push up goal along with the progress they have made
//the method update challenge is used to communicate with database


public class Challenge_Menu extends ActionBarActivity implements View.OnClickListener {

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge__menu);
        View spu_button = findViewById(R.id.start_pu);
        spu_button.setOnClickListener(this);
        View spr_button = findViewById(R.id.start_pacer);
        spr_button.setOnClickListener(this);
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
                Intent intent = new Intent(this, Pushup.class);
                startActivity(intent);
                break;
            }
            case R.id.start_pacer: {
                Intent intent = new Intent(this, PacerRunActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void update_challenge() {
        //read in the data from database about which progress/goals made in challenge
        setBestGoalText();
    }


    //retrieve personal best and goal for current userid, turn font to green when goal achieved.
    private void setBestGoalText() {
        TextView vValue = (TextView) findViewById(R.id.pushBestGoal);
        vValue.setText(ChallengeDatabase.findPushUpPersonalBest(Login.UserId) + "/" + ChallengeDatabase.findPushUpPersonalGoal(Login.UserId));
        if (ChallengeDatabase.findPushUpPersonalBest(Login.UserId) >= ChallengeDatabase.findPushUpPersonalGoal(Login.UserId)) {
            vValue.setTextColor(Color.GREEN);
        }
        TextView tvValue = (TextView) findViewById(R.id.runBestGoal);
        tvValue.setText(ChallengeDatabase.findRunPersonalBest(Login.UserId) + "/" + ChallengeDatabase.findRunPersonalGoal(Login.UserId));
        if (ChallengeDatabase.findRunPersonalBest(Login.UserId) >= ChallengeDatabase.findRunPersonalGoal(Login.UserId)) {
            tvValue.setTextColor(Color.GREEN);
        }

    }

}
