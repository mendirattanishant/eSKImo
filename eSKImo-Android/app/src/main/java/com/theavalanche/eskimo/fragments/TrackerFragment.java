package com.theavalanche.eskimo.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.theavalanche.eskimo.R;
import com.theavalanche.eskimo.Session;
import com.theavalanche.eskimo.info.api.SkiRecordRESTClient;
import com.theavalanche.eskimo.maps.RouteDetailsActivity;
import com.theavalanche.eskimo.models.SkiRecord;

import java.text.DateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TrackerFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    protected static final String TAG = "eskimoTrackerFragment";

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
    protected String mLastUpdateTimeLabel;
    protected Boolean mRequestingLocationUpdates;
    protected String mLastUpdateTime;
    private SkiRecord route;
    private GoogleMap googleMap;
    private Chronometer timer;
    private long elapsedTime;
    private SupportMapFragment mapFragment=null;
    private SkiRecordRESTClient skiRecordRESTClient;
    private String bestProvider;
    private LocationManager locationManager;


    public TrackerFragment(){

    }

    private static final String[] S = { "Out of Service",
            "Temporarily Unavailable", "Available" };


    /**
     *
     * http://stackoverflow.com/questions/25822202/google-api-null-pointer-by-getlastknownlocation
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);

        mapFragment = new SupportMapFragment() {
            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
                googleMap = mapFragment.getMap();
                if (googleMap != null) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//                    Location loc = LocationServices.FusedLocationApi.getLastLocation(
//                            mGoogleApiClient);
                    if(!mGoogleApiClient.isConnected()){
                        mGoogleApiClient.connect();
                    }

                    // add start location marker.
                    LocationManager locManager;
                    String context = Context.LOCATION_SERVICE;
                    Criteria c = new Criteria();
                    c.setAccuracy(Criteria.ACCURACY_FINE);
                    c.setAltitudeRequired(false);
                    c.setBearingRequired(false);
                    c.setCostAllowed(true);
                    c.setPowerRequirement(Criteria.POWER_LOW);

                    locManager = (LocationManager) getActivity().getSystemService(context);
                    String provider = locManager.getBestProvider(c, true);
                    Location loc = locManager.getLastKnownLocation(provider);

                    LatLng currentPosition = updateWithNewLocation(loc);
                    if(currentPosition != null) {
                        Marker startLocation = googleMap.addMarker(new MarkerOptions()
                                .position(currentPosition)
                                .title("Start Location")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 17));
                        route.setStartLocation(new com.theavalanche.eskimo.models.Location(loc));
                    }
                }
            }
        };
        getChildFragmentManager().beginTransaction().add(R.id.map, mapFragment).commit();


        mStartUpdatesButton = (Button) view.findViewById(R.id.start_updates_button);
        mStopUpdatesButton = (Button) view.findViewById(R.id.stop_updates_button);
        mSaveUpdatesButton = (Button) view.findViewById(R.id.save_updates_button);
        mExitUpdatesButton = (Button) view.findViewById(R.id.exit_updates_button);
        mLastUpdateTimeTextView = (TextView) view.findViewById(R.id.last_update_time_text);
        mLastUpdateTimeLabel = getResources().getString(R.string.last_update_time_label);
        timer = (Chronometer) view.findViewById(R.id.chronometer);
        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        // save the state
        updateValuesFromBundle(savedInstanceState);
        buildGoogleApiClient();
        mExitUpdatesButton.setEnabled(true);
        route = new SkiRecord();

        mStartUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mRequestingLocationUpdates) {
                    mRequestingLocationUpdates = true;
                    setButtonsEnabledState();
                    startLocationUpdates();
                    // this should be set only once and not on restart after pause.
                    if (route.getStartTime() == null) {
                        route.setStartTime(new Date());
                    }
                    timer.setBase(SystemClock.elapsedRealtime() + elapsedTime);
                    timer.start();
                }
            }
        });

        mStopUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRequestingLocationUpdates) {
                    mRequestingLocationUpdates = false;
                    setButtonsEnabledState();
                    stopLocationUpdates();
                    // http://stackoverflow.com/questions/5594877/android-chronometer-pause
                    elapsedTime = timer.getBase() - SystemClock.elapsedRealtime();
                    timer.stop();
                    route.setEndTime(new Date());
                }
            }
        });

        mSaveUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(getActivity(), "Saving route details", Toast.LENGTH_SHORT).show();
                                skiRecordRESTClient = new SkiRecordRESTClient();
                                route.setTitle("My Route");
                                route.setUserId(Integer.parseInt(Session.loggedUser.getId()));
                                skiRecordRESTClient.createSkiRecord(route).enqueue(new Callback<SkiRecord>() {
                                    @Override
                                    public void onResponse(Response<SkiRecord> response, Retrofit retrofit) {
                                        Log.d(TAG, "Successfully created Ski Record!");
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        t.printStackTrace();
                                        Log.d(TAG, "Failed creating Ski Record.");
                                    }
                                });


                                mRequestingLocationUpdates = false;
                                setButtonsEnabledState();
                                timer.stop();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Seems you are in an active ski session!").setPositiveButton("End and Save current ski session", dialogClickListener)
                        .setNegativeButton("Continue without saving", dialogClickListener).show();

            }

        });




        mExitUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),RouteDetailsActivity.class);
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
//        locationManager.requestLocationUpdates(bestProvider, 20000, 1, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
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

            updateUI(null);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
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

    private void updateUI(SkiRecord route) {
        if(route == null) {
         return;
        }
        mLastUpdateTimeTextView.setText(String.format("%s: %s", mLastUpdateTimeLabel,
                (Math.floor(route.getDistance() * 100) / 100) + " miles"));
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            route.setStartLocation(new com.theavalanche.eskimo.models.Location(mCurrentLocation));
            updateUI(route);
        }
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        route.addLocaiton(new com.theavalanche.eskimo.models.Location(location));
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        googleMap.addPolyline(new PolylineOptions()
                .addAll(route.getLatLngs())
                .color(Color.RED)
                .geodesic(true)
                .width(6));

        // resetting camaera location to hold the map inside viewport.
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng marker : route.getLatLngs()) {
            builder.include(marker);
        }
        final LatLngBounds bounds = builder.build();
        final int padding = 50;
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(CameraPosition arg0) {
                // Move camera.
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
                // Remove listener to prevent position reset on camera move.
                googleMap.setOnCameraChangeListener(null);
            }
        });

        updateUI(route);
        Toast.makeText(getActivity(), getResources().getString(R.string.location_updated_message),
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
        this.googleMap = map;
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        Location loc = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if(!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
        }
        if(loc != null) {
            // TODO: if location is null we should show some message.Its crashing the app now.
            LatLng currentPosition = updateWithNewLocation(loc);

            Marker startLocation = googleMap.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .title("Start Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 17));
            route.setStartLocation(new com.theavalanche.eskimo.models.Location(loc));
        }
    }

    // Location Received
    private LatLng updateWithNewLocation(Location loc) {
        LatLng currentLocation = null;
        if (loc != null) {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();

            currentLocation = new LatLng(lat, lon);

        } else {
            Log.d(TAG, "No location found");;
        }
        return currentLocation;
    }

}
