package teamjoeys.dbms.umkc.challenge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dvgalarza on 10/31/15.
 */
public class ChallengeDatabaseHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "30DayChallenge.db";
    public SQLiteDatabase database;

    public ChallengeDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    // Creates all tables required for database, if they don't exist already.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ChallengeDatabaseContract.CREATE_SCHEMA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ChallengeDatabaseContract.CREATE_SCHEMA);
    }
}
