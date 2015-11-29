create table application_user(
	user_id integer primary key not null,
	email text not null,
	password text not null
);

create table pushup_workout_goal(
   workoutgoal_id integer primary key autoincrement,
   user_id integer not null,
   numberofpushups integer not null,
   startdate datetime not null,
   foreign key(user_id) references application_user(user_id)
);
   
create table run_workout_goal(
   	workoutgoal_id integer primary key autoincrement,
   	user_id integer not null,
   	workoutgoal_time integer not null,
   	workoutgoal_distance real not null,
   	startdate datetime not null,
   	foreign key(user_id) references application_user(user_id)
);

create table pushup_session(
   pushup_session_id integer primary key autoincrement,
   pushup_goal_id integer not null,
   pushup_session_date datetime not null,
   numberofpushups integer not null,
   foreign key(pushup_goal_id) references pushup_workout_goal(workoutgoal_id)
);
   
create table run_session (
	run_session_id integer primary key not null autoincrement,
	run_goal_id integer not null,
	run_session_date datetime not null,
	run_session_distance real,
	foreign key(run_goal_id) references run_workout_goal(workoutgoal_id)
);

create table run_timestamp (
	run_timestamp_id integer primary key autoincrement,
	run_session_id integer,
	timestamp_time datetime not null,
	timestamp_location text not null,
	foreign key(run_session_id) references run_session(run_session_id)
);