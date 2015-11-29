package teamjoeys.dbms.umkc.challenge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public int CreatePushupGoal(int userId, int pushupAmt) {
        // INSERT INTO pushup_workout_goal(user_id, numberofpushups, startdate)
        // VALUES(userId, pushupAmt, getDateTime())
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("numberofpushups", pushupAmt);
        values.put("startdate", getDateTime());
        long goal_id_result = mDbHelper.insert("pushup_workout_goal", null, values);
        if (goal_id_result == -1) {
            // Unsuccessful
            mDbHelper.close();
            return -1;
        }
        else
        {
            mDbHelper.close();
            return (int) goal_id_result;
        }
    }

    public int CreateRunGoal(int userId, double distanceAmt) {
        // INSERT INTO run_workout_goal(user_id, workoutgoal_distance, startdate)
        // VALUES(userId, distanceAmt, getDateTime())
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("workoutgoal_distance", distanceAmt);
        values.put("startdate", getDateTime());
        long goal_id_result = mDbHelper.insert("run_workout_goal", null, values);
        if (goal_id_result == -1) {
            // Unsuccessful
            mDbHelper.close();
            return -1;
        }
        else
        {
            mDbHelper.close();
            return (int) goal_id_result;
        }
    }

    // returns goal id of latest pushup goal
    public int GetLatestPushupGoal(int userId) {
        //SELECT TOP 1 *
        //FROM pushup_workout_goal
        //WHERE user_id = current_user_id
        //ORDER BY startdate DESC;
        String[] projection = {
                ChallengeDatabaseContract.PushupWorkoutGoal.COLUMN_NAME_WORKOUT_GOAL_ID
        };

        String selection =
                ChallengeDatabaseContract.PushupWorkoutGoal.COLUMN_NAME_WORKOUT_USER_ID +
                        " = " + userId + " ORDER BY startdate DESC";

        Cursor c;

        try {
            c = mDbHelper.query(
                    ChallengeDatabaseContract.PushupWorkoutGoal.TABLE_NAME,
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
            // no goals found
            return -1;
        } else {
            String goalStartDate = c.getString(c.getColumnIndex(ChallengeDatabaseContract.PushupWorkoutGoal.COLUMN_NAME_START_DATE));
            // if getDateTime - goalStartDate < 30
            return c.getInt(c.getColumnIndex(ChallengeDatabaseContract.PushupWorkoutGoal.COLUMN_NAME_WORKOUT_GOAL_ID));
            // else return -1
        }
    }

    //get active run goal for user
    public int GetLatestRunGoal(int userId) {
        //SELECT TOP 1 *
        //FROM run_workout_goal
        //WHERE user_id = userId
        //ORDER BY startdate DESC;
        String[] projection = {
                ChallengeDatabaseContract.RunWorkoutGoal.COLUMN_NAME_WORKOUT_GOAL_ID
        };

        String selection =
                ChallengeDatabaseContract.RunWorkoutGoal.COLUMN_NAME_WORKOUT_USER_ID +
                        " = " + userId + " ORDER BY startdate DESC";

        Cursor c;

        try {
            c = mDbHelper.query(
                    ChallengeDatabaseContract.RunWorkoutGoal.TABLE_NAME,
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
            // no goals found
            return -1;
        } else {
            String goalStartDate = c.getString(c.getColumnIndex(ChallengeDatabaseContract.RunWorkoutGoal.COLUMN_NAME_START_DATE));
            // if getDateTime - goalStartDate < 30
            return c.getInt(c.getColumnIndex(ChallengeDatabaseContract.RunWorkoutGoal.COLUMN_NAME_WORKOUT_GOAL_ID));
            // else return -1
        }
    }

    // returns amount of pushups for the given goal id
    public int GetPushupGoalAmt(int pushupGoalId) {
        // SELECT numberofpushups
        // FROM pushup_workout_goal
        // WHERE workoutgoal_id = pushupGoalId;
        String[] projection = {
                ChallengeDatabaseContract.PushupWorkoutGoal.COLUMN_NAME_NUMBER_OF_PUSHUPS
        };

        String selection =
                ChallengeDatabaseContract.PushupWorkoutGoal.COLUMN_NAME_WORKOUT_GOAL_ID +
                        " = " + pushupGoalId;

        Cursor c;

        try {
            c = mDbHelper.query(
                    ChallengeDatabaseContract.PushupWorkoutGoal.TABLE_NAME,
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
            // no goals found
            return -1;
        } else {
            return c.getInt(c.getColumnIndex(ChallengeDatabaseContract.PushupWorkoutGoal.COLUMN_NAME_NUMBER_OF_PUSHUPS));
        }
    }

    public double GetRunGoalDistance(int runGoalId) {
        // SELECT workoutgoal_distance
        // FROM run_workout_goal
        // WHERE workoutgoal_id = runGoalId;
        String[] projection = {
                ChallengeDatabaseContract.RunWorkoutGoal.COLUMN_NAME_WORKOUT_GOAL_DISTANCE
        };

        String selection =
                ChallengeDatabaseContract.RunWorkoutGoal.COLUMN_NAME_WORKOUT_GOAL_ID +
                        " = " + runGoalId;

        Cursor c;

        try {
            c = mDbHelper.query(
                    ChallengeDatabaseContract.RunWorkoutGoal.TABLE_NAME,
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
            // no goals found
            return -1;
        } else {
            return c.getDouble(c.getColumnIndex(ChallengeDatabaseContract.RunWorkoutGoal.COLUMN_NAME_WORKOUT_GOAL_DISTANCE));
        }
    }

    public double GetRunGoalTime(int runGoalId) {
        // SELECT workoutgoal_time
        // FROM run_workout_goal
        // WHERE workoutgoal_id = runGoalId;
        String[] projection = {
                ChallengeDatabaseContract.RunWorkoutGoal.COLUMN_NAME_WORKOUT_GOAL_TIME
        };

        String selection =
                ChallengeDatabaseContract.RunWorkoutGoal.COLUMN_NAME_WORKOUT_GOAL_ID +
                        " = " + runGoalId;

        Cursor c;

        try {
            c = mDbHelper.query(
                    ChallengeDatabaseContract.RunWorkoutGoal.TABLE_NAME,
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
            // no goals found
            return -1;
        } else {
            return c.getDouble(c.getColumnIndex(ChallengeDatabaseContract.RunWorkoutGoal.COLUMN_NAME_WORKOUT_GOAL_TIME));
        }
    }

    //traverse database table to retrieve personal best push up record
    public int GetBestPushupSession(int userId, int pushupGoalId) {
        // SELECT MAX(s.numberofpushups)
        // FROM pushup_workout_goal g JOIN pushup_session s ON g.workoutgoal_id = s.pushup_goal_id
        // WHERE g.user_id = userId AND g.workoutgoal_id = pushupGoalId;
        return -1;
    }

    //traverse database table to retrieve personal best run record
    public double GetBestRunSession(int userId, int runGoalId) {
        // SELECT MAX(rs.run_session_distance)
        // FROM run_workout_goal g JOIN run_session s ON g.workoutgoal_id = s.run_goal_id
        // WHERE w.user_id = userId AND w.workoutgoal_id = runGoalId;
        return -1;
    }

    // Returns session id of the given user's most recent pushup session.
    public int GetLatestPushupSession(int userId, int pushupGoalId) {
        // SELECT pushup_session_id
        // FROM pushup_session
        // WHERE

        return -1;
    }

    public int GetLatestRunSession(int userId, int runGoalId) {

        return -1;
    }

    public int AddPushupSession(int pushupGoalId, int numPushups) {
        //INSERT INTO pushup_session(pushup_goal_id, pushup_session_date, numberofpushups)
        //VALUES([current_goal_id], CURRENT_TIMESTAMP, [pushupscompleted]);
        ContentValues values = new ContentValues();
        values.put("pushup_goal_id", pushupGoalId);
        values.put("pushup_session_date", getDateTime());
        values.put("numberofpushups", numPushups);
        long session_id_result = mDbHelper.insert("pushup_session", null, values);
        if (session_id_result == -1) {
            // Unsuccessful
            mDbHelper.close();
            return -1;
        }
        else
        {
            mDbHelper.close();
            return (int) session_id_result;
        }
    }

    public int AddRunSession(int runGoalId, double runDistance, double runTime) {
        // INSERT INTO run_session(run_goal_id, run_session_date, run_session_distance, run_session_time)
        // VALUES(runGoalId, getDateTime(), runDistance, runTime)
        ContentValues values = new ContentValues();
        values.put("run_goal_id", runGoalId);
        values.put("run_session_date", getDateTime());
        values.put("run_session_distance", runDistance);
        values.put("run_session_time", runTime);
        long session_id_result = mDbHelper.insert("run_session", null, values);
        if (session_id_result == -1) {
            // Unsuccessful
            mDbHelper.close();
            return -1;
        }
        else
        {
            mDbHelper.close();
            return (int) session_id_result;
        }
    }
}
