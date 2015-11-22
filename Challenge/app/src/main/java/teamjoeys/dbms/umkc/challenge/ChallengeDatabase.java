package teamjoeys.dbms.umkc.challenge;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Greg on 10/30/2015.
 */
public class ChallengeDatabase {
    private ChallengeDatabaseHelper mDbHelper;

    public ChallengeDatabase(Context context)
    {
        mDbHelper = new ChallengeDatabaseHelper(context);
    }

    // Finds user specified by email address and password, and returns the user id.
    public int FindUser(String emailAddress, String password) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                ChallengeDatabaseContract.ApplicationUser.COLUMN_NAME_USER_ID
        };

        String selection =
                ChallengeDatabaseContract.ApplicationUser.COLUMN_NAME_EMAIL +
                " = " + emailAddress + " AND " +
                ChallengeDatabaseContract.ApplicationUser.COLUMN_NAME_PASSWORD +
                " = " + password;

        Cursor c = db.query(
                ChallengeDatabaseContract.ApplicationUser.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null);

        if (!(c.moveToFirst()) || c.getCount() ==0){
            // user not found
            return -1;
        }
        else
        {
            return c.getInt(c.getColumnIndex(ChallengeDatabaseContract.ApplicationUser.COLUMN_NAME_USER_ID));
        }
    }

}
