package teamjoeys.dbms.umkc.challenge;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.os.SystemClock;
import android.widget.Toast;
import android.location.Location;

import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import java.text.DateFormat;
import java.util.Date;



/**
 * Created by dvgalarza on 11/23/15.
 */
public class PacerRunActivity extends ActionBarActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private double mDistanceRan;
    private Chronometer my_chronometer;
    private Handler mHandler;
    private int mInterval = 10000;

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private double mLatitude;
    private double mLongitude;
    private LocationRequest mLocationRequest;
    private String mLastUpdateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacer_run);
        my_chronometer = (Chronometer) findViewById(R.id.chronometer);
        View S_button = findViewById(R.id.start_button);
        S_button.setOnClickListener(this);
        View F_button = findViewById(R.id.finish_button);
        F_button.setOnClickListener(this);
        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_button) {
            my_chronometer.setBase(SystemClock.elapsedRealtime());
            my_chronometer.start(); // start timer
            // Every 10 seconds, record new timestamp
            // distanceBetweenPoints = distance from prev_timestamp to current_timestamp
            // mDistanceRan += distanceBetweenPoints;
            createLocationRequest();

            // set time num label to current_timestamp time
            // set distance num label to mDistanceRan (make green if goal reached, blue otherwise)
            mHandler = new Handler();
            startRepeatingTask();

        }
        if (v.getId() == R.id.finish_button) {
            my_chronometer.stop();
            stopRepeatingTask();
            stopLocationUpdates();
            // mDb.CreateRunSession
        }
    }


        Runnable mStatusChecker = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Distance Recorded", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        };



    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }




    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mCurrentLocation != null) {
            mLatitude = mCurrentLocation.getLatitude();
            mLongitude = mCurrentLocation.getLongitude();
        }
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }
}

