package teamjoeys.dbms.umkc.challenge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Created by Greg on 10/30/2015.
 */
public class ChallengeDatabase {
    private SQLiteDatabase mDbHelper;

    public ChallengeDatabase(Context context)
    {
        mDbHelper = new ChallengeDatabaseHelper(context).getWritableDatabase();
    }

    // Finds user specified by email address and password, and returns the user id.
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
        }
        catch(SQLiteException e) {
            return -1;
        }


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
