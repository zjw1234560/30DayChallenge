package teamjoeys.dbms.umkc.challenge;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

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
            Intent intent = new Intent(this,About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_pu: {
                Intent intent = new Intent(this,Pushup.class);
                startActivity(intent);
            }
        }
    }

    private void update_challenge(){
        //read in the data from database about which progress/goals made in challenge
        TextView t = (TextView) findViewById(R.id.day);
        t.setText("Day: " + Integer.toString(count));
        TextView v = (TextView) findViewById(R.id.pu_goal);
        v.setText("Push Up Goal");
        TextView tv = (TextView) findViewById(R.id.run_goal);
        tv.setText("Run Goal");
    }
}
