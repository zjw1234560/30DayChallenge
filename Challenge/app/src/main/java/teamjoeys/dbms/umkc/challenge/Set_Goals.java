package teamjoeys.dbms.umkc.challenge;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Set_Goals extends ActionBarActivity implements View.OnClickListener  {


    public static String push_up_goal;
    public static String run_distance;
    public static String run_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__goals);
        View loginBtn = findViewById(R.id.To_Menu);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set__goals, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.To_Menu: {
                TextView dValue = (TextView) findViewById(R.id.mile_goal);
                run_distance = dValue.getText().toString();
                TextView tValue = (TextView) findViewById(R.id.time_goal);
                run_time = tValue.getText().toString();
                TextView vValue = (TextView) findViewById(R.id.push_goal);
                push_up_goal = vValue.getText().toString();

                Intent intent = new Intent(this, Challenge_Menu.class);
                startActivity(intent);
            }
        }
    }

}
