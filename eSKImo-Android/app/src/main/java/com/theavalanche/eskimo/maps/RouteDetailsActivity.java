package com.theavalanche.eskimo.maps;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.theavalanche.eskimo.Session;
import com.theavalanche.eskimo.info.api.SkiRecordRESTClient;
import com.theavalanche.eskimo.models.SkiRecord;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RouteDetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    protected TextView mSessionStartTimeTextView;
    protected TextView mSessionStopTimeTextView;
    protected TextView mSessionDurationTextView;
    protected Button mExitButton;
    private SkiRecordRESTClient skiRecordRESTClient;
    private List<SkiRecord> skiRecords;
    protected static final String TAG = "RouteDetailsActivity";
    GoogleMap gmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mExitButton = (Button) findViewById(R.id.exit_session_details_button);
        mExitButton.setEnabled(true);
        mSessionStartTimeTextView = (TextView) findViewById(R.id.start_time_text);
        mSessionStopTimeTextView = (TextView) findViewById(R.id.stop_time_text);
        mSessionDurationTextView = (TextView) findViewById(R.id.duration_text);
        updateRouteDetails();
    }

    public void exitSessionButtonHandler(View view) {
        finish();
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        this.gmap = map;
        refreshSkiRecords();
        updateRouteDetails();
    }

    private void updateRouteDetails() {
        //Need to be calculated from the history records
        double distance = 0;
        if(skiRecords ==  null){
            return;
        }
        for(SkiRecord skiRecord: skiRecords){
            distance+=skiRecord.getDistance();
        }
        mSessionStartTimeTextView.setText("Total Ski trips: "+skiRecords.size());
        mSessionStopTimeTextView.setText("Total distance skied: "+Math.floor((distance* 100)/100) +"miles");
        mSessionDurationTextView.setText("My Ski History");
    }

    private void refreshSkiRecords() {
        skiRecordRESTClient = new SkiRecordRESTClient();
        skiRecordRESTClient.getSkiRecordsByUserId(Session.loggedUser.getId()).enqueue(new Callback<List<SkiRecord>>() {
            @Override
            public void onResponse(Response<List<SkiRecord>> response, Retrofit retrofit) {
                Log.d(TAG, "Successfully fetched Ski Record!");
                skiRecords = response.body();

                if(skiRecords != null && skiRecords.size() > 0) {
                    gmap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    for(SkiRecord skiRecord : skiRecords) {
                        List<LatLng> routePoints = PolyUtil.decode(skiRecord.getPath());
                        gmap.addPolyline(new PolylineOptions()
                                .addAll(routePoints)
                                .color(Color.RED)
                                .geodesic(true)
                                .width(6));


                        // check for wifi here else this will crash
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (LatLng marker : routePoints) {
                            builder.include(marker);
                        }


                        final LatLngBounds bounds = builder.build();
                        final int padding = 50;
                        gmap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                            @Override
                            public void onCameraChange(CameraPosition arg0) {
                                // Move camera.
                                gmap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
                                // Remove listener to prevent position reset on camera move.
                                gmap.setOnCameraChangeListener(null);
                            }
                        });
                    }
                    updateRouteDetails();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "Failed fetching Ski Record.");
            }
        });
    }
}