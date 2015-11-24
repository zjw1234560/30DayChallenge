package teamjoeys.dbms.umkc.challenge;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//this is the login screen for the challenge app
//the login uses email and password, followed by a log in button for execution

public class Login extends ActionBarActivity implements View.OnClickListener {

    private ChallengeDatabase mDb;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View L_button = findViewById(R.id.login_exe);
        L_button.setOnClickListener(this);
        View C_button = findViewById(R.id.create_exe);
        C_button.setOnClickListener(this);

        mContext = this;
        mDb = new ChallengeDatabase(mContext);
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
            Intent intent = new Intent(this,About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        //pass login information to server and get response if login should proceed
        switch (v.getId()) {
            case R.id.login_exe: {
                EditText email = (EditText) findViewById(R.id.email);
                EditText password = (EditText) findViewById(R.id.password);
                String user_email = email.getText().toString();
                String user_password = password.getText().toString();
                int user_id = mDb.FindUser(user_email, user_password);
                if (user_id == -1) {
                    String text = "Account Info Incorrect";
                    // Use a toast message to tell user account info wasn't found
                    Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(this, Challenge_Menu.class);
                    startActivity(intent);
                }
                else {
                    String text = "Login Successful!";
                    // Use a toast message to tell user login was success
                    Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(this, Challenge_Menu.class);
                    startActivity(intent);
                }
            }
            case R.id.create_exe: {
                //mDb.CreateUser(email, pw);
                String text = "Account Created!";
                // Use a toast message to tell user account creation was successful
                Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(this, Challenge_Menu.class);
                startActivity(intent);
            }
        }
    }
}
