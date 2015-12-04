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
        } else {
            return c.getInt(c.getColumnIndex(ChallengeDatabaseContract.ApplicationUser.COLUMN_NAME_USER_ID));
        }
    }

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

    public void CreatePushupGoal(int userId, int pushupAmt) {
        // INSERT INTO pushup_workout_goal()
        // VALUES()
    }

    public void CreateRunGoal(int userId, double distanceAmt) {
        // INSERT INTO run_workout_goal()
        // VALUES()
    }

    //traverse database table to retrieve personal push up goal
    public int GetLatestPushupGoal(int userId) {
        //SELECT TOP 1 *
        //FROM pushup_workout_goal
        //WHERE user_id = current_user_id
        //ORDER BY startdate DESC;

        // ensure less than 30 days after startdate
        return -1;
    }

    //get active run goal for user
    public int GetLatestRunGoal(int userId) {
        //SELECT TOP 1 *
        //FROM run_workout_goal
        //WHERE user_id = userId
        //ORDER BY startdate DESC;

        // ensure less than 30 days after startdate
        return -1;
    }

    public int GetPushupGoalAmt(int pushupGoalId) {
        // SELECT numberofpushups
        // FROM pushup_workout_goal
        // WHERE workoutgoal_id = pushupGoalId;
        return -1;
    }

    //traverse database table to retrieve personal best push up record
    public int GetBestPushupSession(int userId, int pushupGoalId) {
        // SELECT MAX(s.numberofpushups)
        // FROM pushup_workout_goal g JOIN pushup_session s ON g.workoutgoal_id = s.pushup_goal_id
        // WHERE g.user_id = userId AND g.workoutgoal_id = pushupGoalId;
        return -1;
    }

    public double GetRunGoalAmt(int runGoalId) {
        // SELECT workoutgoal_distance
        // FROM run_workout_goal
        // WHERE workoutgoal_id = runGoalId;
        return -1;
    }

    //traverse database table to retrieve personal best run record
    public double GetBestRunSession(int userId, int runGoalId) {
        // SELECT MAX(rs.run_session_distance)
        // FROM run_workout_goal g JOIN run_session s ON g.workoutgoal_id = s.run_goal_id
        // WHERE w.user_id = userId AND w.workoutgoal_id = runGoalId;
        return -1;
    }

    public void AddPushupSession(int pushupGoalId, int numPushups) {
        //INSERT INTO pushup_session(pushup_goal_id, pushup_session_date, numberofpushups)
        //VALUES([current_goal_id], CURRENT_TIMESTAMP, [pushupscompleted]);
    }
}
