package com.theavalanche.eskimo.maps;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.theavalanche.eskimo.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RouteActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    protected static final String TAG = "eskimo - route activity";

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";

    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected Location mCurrentLocation;

    protected Button mStartUpdatesButton;
    protected Button mStopUpdatesButton;
    protected Button mSaveUpdatesButton;
    protected Button mExitUpdatesButton;
    protected TextView mLastUpdateTimeTextView;
    protected TextView mLatitudeTextView;
    protected TextView mLongitudeTextView;

    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected String mLastUpdateTimeLabel;
    protected Boolean mRequestingLocationUpdates;
    protected String mLastUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mStartUpdatesButton = (Button) findViewById(R.id.start_updates_button);
        mStopUpdatesButton = (Button) findViewById(R.id.stop_updates_button);
        mSaveUpdatesButton = (Button) findViewById(R.id.save_updates_button);
        mExitUpdatesButton = (Button) findViewById(R.id.exit_updates_button);
        mLatitudeTextView = (TextView) findViewById(R.id.latitude_text);
        mLongitudeTextView = (TextView) findViewById(R.id.longitude_text);
        mLastUpdateTimeTextView = (TextView) findViewById(R.id.last_update_time_text);

        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLastUpdateTimeLabel = getResources().getString(R.string.last_update_time_label);

        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        // save the state
        updateValuesFromBundle(savedInstanceState);
        buildGoogleApiClient();
        mExitUpdatesButton.setEnabled(true);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
                setButtonsEnabledState();
            }

            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }
            updateUI();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void startUpdatesButtonHandler(View view) {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            setButtonsEnabledState();
            startLocationUpdates();
        }
    }

    public void stopUpdatesButtonHandler(View view) {
        if (mRequestingLocationUpdates) {
            mRequestingLocationUpdates = false;
            setButtonsEnabledState();
            stopLocationUpdates();
        }
    }

    public void saveUpdatesButtonHandler(View view) {
        Toast.makeText(this, "Saving route details", Toast.LENGTH_SHORT).show();
        // save and go tto profile page may be?
        Intent i = new Intent(this,RouteDetailsActivity.class);
        startActivity(i);
        finish();
    }

    public void exitUpdatesButtonHandler(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent i = new Intent(RouteActivity.this,RouteDetailsActivity.class);
                        startActivity(i);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    private void setButtonsEnabledState() {
        if (mRequestingLocationUpdates) {
            mStartUpdatesButton.setEnabled(false);
            mStopUpdatesButton.setEnabled(true);
            mSaveUpdatesButton.setEnabled(true);
            mExitUpdatesButton.setEnabled(true);
        } else {
            mStartUpdatesButton.setEnabled(true);
            mStopUpdatesButton.setEnabled(false);
            mSaveUpdatesButton.setEnabled(false);
            mExitUpdatesButton.setEnabled(true);
        }
    }

    private static List<String> latitudes = new ArrayList<String>();
    private static List<String> longitudes = new ArrayList<String>();

    private void updateUI() {
        latitudes.add(Double.toString(mCurrentLocation.getLatitude()));
        longitudes.add(Double.toString(mCurrentLocation.getLongitude()));
        mLatitudeTextView.setText(String.format("%s: %s", mLatitudeLabel,
                latitudes.toString()));
        mLongitudeTextView.setText(String.format("%s: %s", mLongitudeLabel,
                longitudes.toString()));
        mLastUpdateTimeTextView.setText(String.format("%s: %s", mLastUpdateTimeLabel,
                mLastUpdateTime));
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            updateUI();
        }
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();
        Toast.makeText(this, getResources().getString(R.string.location_updated_message),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onMapReady(final GoogleMap map) {
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        List<LatLng> routePoints = PolyUtil.decode(getRouteData());
        map.addPolyline(new PolylineOptions()
                .addAll(routePoints)
                .color(Color.RED)
                .geodesic(true)
                .width(6));

        // check for wifi here else this will crash !!
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng marker : routePoints) {
            builder.include(marker);
        }
        final LatLngBounds bounds = builder.build();
        final int padding = 50;
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition arg0) {
                // Move camera.
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
                // Remove listener to prevent position reset on camera move.
                map.setOnCameraChangeListener(null);
            }
        });
    }

    private String getRouteData() {
        return "ktwnE|}ypUYd@i@fAlAfApAhAy@|BwAfE{BnG_ArCEh@Y`Au@vBGPIXwBlG}B~Gm@tAw@xBuAfEsDpKi@tA{AjEmAlDg@rAGj@@~A?fA?dBc@Ku@EyG?uBBwA@kE?{@DaB@uA@AnC?fE@pF@tN@vEIxAIx@YpAEv@?~D@|L@vNBhL@zK?nEAxAAjI?xEG`FBbI?tEAjE@jE@jBLfJDjGFdBCjAEp@Mt@yAnGyC`LuAbF[tAy@bDOdBIfC_AhOWfEu@|DkB~JyBbLw@dEC`B@`CAbDKpPWvZGhMGfDKdNe@bx@EhIArAMbBc@bEsAtN_@tGs@tMWzE[fFyA`Wi@jJHzBHvCDdD@rK@jLBdTC|Q?bFAlAvA?|RF`BJ@pD?tK@jEAlE?pE@|DH~BV`DZtCr@jDhBfInAhFpA~Fz@|Dr@nCVx@`AxBvErJdClFhB|D?LJBnB~D|@lBrG|M~CxGpEfJbCxE|@pBvD~HfBnDdBvDfChF`DzHfCzGh@zAr@dBjBdFvBbGvAvDxEfMrJvVhAvCBFd@vA`AbC`CbGpA~DdAzBhDzGhEvK~L`[lDbJd@tAd@nABb@O\\[fAgBrFtAn@`@d@NZPz@VxA\\tBX`BRf@JJRpBn@fFn@hFz@fGp@jE`@~B~BvM~AjJtBvLnCvOhAdGPv@XrA^jAzAlDr@xAvCrElF`HdHfJ`IdKX`@BTBL^p@`@n@Tf@f@`BJj@N|AJx@N\\NV@Lf@h@~AhCd@r@FJq@z@aCrCoEtFoDpEW^d@r@z@fAt@~@fA|AnAzAnBhC|BtCbAxAvCkD`EcF|CuDVYpDuExFaH`BoBj@[l@bAh@z@p@dAtC|EdCzDnArBbA|A{DrDeJvJgAlAgBlB}A`BETBXf@t@hAzADJcBlB{B`CKOa@m@SYtC}CDG";
    }
}