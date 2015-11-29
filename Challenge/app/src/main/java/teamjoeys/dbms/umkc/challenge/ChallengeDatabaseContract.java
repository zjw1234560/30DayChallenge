package teamjoeys.dbms.umkc.challenge;

import android.provider.BaseColumns;

/**
 * Created by dvgalarza on 10/31/15.
 */
public final class ChallengeDatabaseContract {
    public ChallengeDatabaseContract() {}

    public static final String CREATE_SCHEMA =
            ApplicationUser.SQL_CREATE_TABLE + "\n" +
            PushupWorkoutGoal.SQL_CREATE_TABLE + "\n" +
            RunWorkoutGoal.SQL_CREATE_TABLE + "\n" +
            PushupSession.SQL_CREATE_TABLE + "\n" +
            RunSession.SQL_CREATE_TABLE + "\n" +
            RunTimestamp.SQL_CREATE_TABLE;

    public static final String CREATE_DEFAULT_USER = "INSERT INTO application_user VALUES(55, 'hello@there', 'pw54');";

    public static abstract class ApplicationUser implements BaseColumns {
        public static final String TABLE_NAME = "application_user";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";

        private static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS application_user(\n" +
                        "\tuser_id integer primary key not null,\n" +
                        "\temail text not null,\n" +
                        "\tpassword text not null\n" +
                        ");";

        private static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS application_user";
    }

    public static abstract class PushupWorkoutGoal implements BaseColumns {
        public static final String TABLE_NAME = "pushup_workout_goal";
        public static final String COLUMN_NAME_WORKOUT_GOAL_ID = "workoutgoal_id";
        public static final String COLUMN_NAME_WORKOUT_USER_ID = "user_id";
        public static final String COLUMN_NAME_NUMBER_OF_PUSHUPS = "numberofpushups";
        public static final String COLUMN_NAME_START_DATE = "startdate";

        private static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS pushup_workout_goal(\n" +
                        "\tworkoutgoal_id integer primary key autoincrement,\n" +
                        "\tuser_id integer not null,\n" +
                        "\tnumberofpushups integer not null,\n" +
                        "\tstartdate datetime not null,\n" +
                        "\tforeign key(user_id) references application_user(user_id)\n" +
                        ");";

        private static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS pushup_workout_goal";
    }

    public static abstract class RunWorkoutGoal implements BaseColumns {
        public static final String TABLE_NAME = "run_workout_goal";
        public static final String COLUMN_NAME_WORKOUT_GOAL_ID = "workoutgoal_id";
        public static final String COLUMN_NAME_WORKOUT_USER_ID = "user_id";
        public static final String COLUMN_NAME_WORKOUT_GOAL_TIME = "workoutgoal_time";
        public static final String COLUMN_NAME_WORKOUT_GOAL_DISTANCE = "workoutgoal_distance";
        public static final String COLUMN_NAME_START_DATE = "startdate";

        private static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS run_workout_goal(\n" +
                        "\tworkoutgoal_id integer primary key autoincrement,\n" +
                        "\tuser_id integer not null,\n" +
                        "\tworkoutgoal_time integer not null,\n" +
                        "\tworkoutgoal_distance real not null,\n" +
                        "\tstartdate datetime not null,\n" +
                        "\tforeign key(user_id) references application_user(user_id)\n" +
                        ");";

        private static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS run_workout_goal";
    }

    public static abstract class PushupSession implements BaseColumns {
        public static final String TABLE_NAME = "pushup_session";
        public static final String COLUMN_NAME_PUSHUP_SESSION_ID = "pushup_session_id";
        public static final String COLUMN_NAME_PUSHUP_GOAL_ID = "pushup_goal_id";
        public static final String COLUMN_NAME_PUSHUP_SESSION_DATE = "pushup_session_date";
        public static final String COLUMN_NAME_NUMBER_OF_PUSHUPS = "numberofpushups";

        private static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS pushup_session(\n" +
                        "\tpushup_session_id integer primary key autoincrement,\n" +
                        "\tpushup_goal_id integer not null,\n" +
                        "\tpushup_session_date datetime not null,\n" +
                        "\tnumberofpushups integer not null,\n" +
                        "\tforeign key(pushup_goal_id) references pushup_workout_goal(workoutgoal_id)\n" +
                        ");";

        private static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS pushup_session";
    }

    public static abstract class RunSession implements BaseColumns {
        public static final String TABLE_NAME = "run_session";
        public static final String COLUMN_NAME_RUN_SESSION_ID = "run_session_id";
        public static final String COLUMN_NAME_RUN_GOAL_ID = "run_goal_id";
        public static final String COLUMN_NAME_RUN_SESSION_DATE = "run_session_date";

        private static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS run_session (\n" +
                        "\trun_session_id integer primary key not null autoincrement,\n" +
                        "\trun_goal_id integer not null,\n" +
                        "\trun_session_date datetime not null,\n" +
                        "\trun_session_distance real,\n" +
                        "\tforeign key(run_goal_id) references run_workout_goal(workoutgoal_id)\n" +
                        ");";

        private static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS run_session";
    }

    public static abstract class RunTimestamp implements BaseColumns {
        public static final String TABLE_NAME = "run_timestamp";
        public static final String COLUMN_NAME_RUN_TIMESTAMP_ID = "run_timestamp_id";
        public static final String COLUMN_NAME_RUN_SESSION_ID = "run_session_id";
        public static final String COLUMN_NAME_TIMESTAMP_TIME = "timestamp_time";
        public static final String COLUMN_NAME_TIMESTAMP_LOCATION = "timestamp_location";

        private static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS run_timestamp (\n" +
                        "\trun_timestamp_id integer primary key autoincrement,\n" +
                        "\trun_session_id integer,\n" +
                        "\ttimestamp_time datetime not null,\n" +
                        "\ttimestamp_location text not null,\n" +
                        "\tforeign key(run_session_id) references run_session(run_session_id)\n" +
                        ");";

        private static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS run_timestamp";
    }
}
