--Find Login:

SELECT user_id
FROM application_user
WHERE email = [email_box_value] AND password = [pw_box_value]
LIMIT 1;

	-- [Catch SQLException] If no user found, throw dialog box,
	-- would you like to create a user with the given name and password?
	-- If yes:
	
	INSERT INTO application_user
	VALUES(user_id_num, email_box_value, pw_box_value);

--Find Latest Pushup Goal:

SELECT TOP 1 *
FROM pushup_workout_goal
WHERE user_id = current_user_id
ORDER BY startdate DESC;

--Find Latest Run Goal:

SELECT TOP 1 *
FROM run_workout_goal
WHERE user_id = current_user_id
ORDER BY startdate DESC;

--Create New Pushup Goal:

INSERT INTO pushup_workout_goal(user_id, numberofpushups, startdate)
VALUES([current_user_id], [pushup_box_value], CURRENT_TIMESTAMP);

--Create New Run Goal:

INSERT INTO run_workout_goal(user_id, workoutgoal_time, workoutgoal_distance, startdate)
VALUES([current_user_id], [time_box_value], [distance_box_value], CURRENT_TIMESTAMP);

--Add Pushup Session:

INSERT INTO pushup_session(pushup_goal_id, pushup_session_date, numberofpushups)
VALUES([current_goal_id], CURRENT_TIMESTAMP, [pushupscompleted]);

--Add Run Session:

INSERT INTO run_session(run_goal_id, run_session_date)
VALUES([current_goal_id], CURRENT_TIMESTAMP);

--Add Run Timestamp:

INSERT INTO run_timestamp(run_session_id, timestamp_time, timestamp_location)
VALUES([current_session_id], CURRENT_TIMESTAMP, [current_location]);
