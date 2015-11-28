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

    //this static field will be storing the current userId and used in other screens
    public static int UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View loginBtn = findViewById(R.id.login_exe);
        loginBtn.setOnClickListener(this);
        View createBtn = findViewById(R.id.create_exe);
        createBtn.setOnClickListener(this);

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
            Intent settingsIntent = new Intent(this, About.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        String user_email = email.getText().toString();
        String user_password = password.getText().toString();
        switch (v.getId()) {
            //pass login information to server and get response if login should proceed
            case R.id.login_exe: {
                // Search DB for user credentials
                int user_id = mDb.FindUser(user_email, user_password);

                // If user not found in DB
                if (user_id == -1) {
                    String text = "Account Info Incorrect";

                    // Use a toast message to tell user account info wasn't found
                    Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    //set UserId to current logged in user
                    UserId = user_id;
                    String text = "Login Successful!";

                    // Use a toast message to tell user login was success, then go to main menu
                    Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(this, Challenge_Menu.class);
                    startActivity(intent);
                }
                return;
            }
            case R.id.create_exe: {
                int user_id_result = mDb.CreateUser(user_email, user_password);
                if(user_id_result == -1) // If user can't be created
                {
                    String txtFail = "Cannot create account with those credentials.";
                    Toast toastFail = Toast.makeText(this, txtFail, Toast.LENGTH_SHORT);
                    toastFail.show();
                }
                else {
                    UserId = user_id_result;
                    String txtSuccess = "Account Created!";
                    Toast toastSuccess = Toast.makeText(this, txtSuccess, Toast.LENGTH_SHORT);
                    toastSuccess.show();
                    Intent challengeMenuIntent = new Intent(this, Challenge_Menu.class);
                    startActivity(challengeMenuIntent);
                }
                return;
            }
        }
    }
}
