package teamjoeys.dbms.umkc.challenge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.EditText;

/**
 * Created by Greg on 10/30/2015.
 */
public class ChallengeDatabase {
    private SQLiteDatabase mDbHelper;

    public ChallengeDatabase(Context context) {
        mDbHelper = new ChallengeDatabaseHelper(context).getWritableDatabase();
    }

    // Finds user specified by email address and password, and returns the user id.
    // Returns -1 if user cannot be found.
    public int FindUser(String emailAddress, String password) {
        String[] projection = {
                ChallengeDatabaseContract.ApplicationUser.COLUMN_NAME_USER_ID
        };

        String selection =
                ChallengeDatabaseContract.ApplicationUser.COLUMN_NAME_EMAIL +
                        " = '" + emailAddress + "' AND " +
                        ChallengeDatabaseContract.ApplicationUser.COLUMN_NAME_PASSWORD +
                        " = '" + password + "'";

        Cursor c;
        try {
            c = mDbHelper.query(
                    ChallengeDatabaseContract.ApplicationUser.TABLE_NAME,
                    projection,
                    selection,
                    null,
                    null,
                    null,
                    null);
        } catch (SQLiteException e) {
            return -1;
        }


        if (!(c.moveToFirst()) || c.getCount() == 0) {
            // user not found
            return -1;
<<<<<<< Updated upstream
        } else {
=======
        }
        else {
>>>>>>> Stashed changes
            return c.getInt(c.getColumnIndex(ChallengeDatabaseContract.ApplicationUser.COLUMN_NAME_USER_ID));
        }
    }

<<<<<<< Updated upstream
    //traverse database table to retrieve personal best push up record
    public static int findPushUpPersonalBest(int userid)
    {
        return 1;
    }

    //traverse database table to retrieve personal push up goal
    public static int findPushUpPersonalGoal(int userid)
    {
        return 2;
    }

    //traverse database table to retrieve personal best run record. Maybe we should round up
    //the return value to 1 digit precision
    public static double findRunPersonalBest(int userid)
    {
        return 2.2;
    }

    //traverse database table to retrieve personal best run goal. Maybe we should round up
    //the return value to 1 digit precision
    public static double findRunPersonalGoal(int userid)
    {
        return 2.2;
    }

=======
    // Inserts new user into DB
    // Returns user id of new user, -1 if unsuccessful creation
    public int CreateUser(String emailAddress, String password) {
        // INSERT INTO application_user
        // VALUES(email_box_value, pw_box_value);
        ContentValues values = new ContentValues();
        values.put("email", emailAddress);
        values.put("password", password);
        long user_id_result = mDbHelper.insert("application_user", null, values);
        if (user_id_result == -1) {
            // Unsuccessful
            mDbHelper.close();
            return -1;
        }
        else
        {
            mDbHelper.close();
            return (int) user_id_result;
        }
    }
>>>>>>> Stashed changes
}
