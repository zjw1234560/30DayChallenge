package teamjoeys.dbms.umkc.challenge;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

//this is the login screen for the challenge app
//the login uses email and password, followed by a log in button for execution

public class Login extends ActionBarActivity implements View.OnClickListener {

    private ChallengeDatabase mDb = new ChallengeDatabase(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View L_button = findViewById(R.id.login_exe);
        L_button.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
        //pass login information to server and get response if login should proceed
        switch (v.getId()) {
            case R.id.login_exe: {
                Intent intent = new Intent(this, Challenge_Menu.class);
                EditText email = (EditText) findViewById(R.id.email);
                EditText password = (EditText) findViewById(R.id.password);
                String user_email = email.getText().toString();
                String user_password = password.getText().toString();
            }
        }
    }
}
