package com.theavalanche.eskimo.maps;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.List;

public class RouteGroupActivity extends FragmentActivity implements OnMapReadyCallback {

    protected TextView mSessionStartTimeTextView;
    protected TextView mSessionStopTimeTextView;
    protected TextView mSessionDurationTextView;
    protected Button mExitButton;
    private static int mapCount=0;
    private static int[] colors = { Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_group);
        SupportMapFragment mapFragment1 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment1.getMapAsync(this);

        SupportMapFragment mapFragment2 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment2.getMapAsync(this);

        SupportMapFragment mapFragment3 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map3);
        mapFragment3.getMapAsync(this);

        SupportMapFragment mapFragment4 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map4);
        mapFragment4.getMapAsync(this);

        mExitButton = (Button) findViewById(R.id.exit_session_details_button);
        mExitButton.setEnabled(true);
        mSessionStartTimeTextView = (TextView) findViewById(R.id.start_time_text);
        mSessionStopTimeTextView = (TextView) findViewById(R.id.stop_time_text);
        mSessionDurationTextView = (TextView) findViewById(R.id.duration_text);
        updateRouteDetails();
    }

    public void exitSessionButtonHandler(View view) {
        Toast.makeText(this, "Goto profile details", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,RouteActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        mapCount = ++mapCount == 4 ? 0: mapCount;

        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        List<LatLng> routePoints = PolyUtil.decode(getRouteData(mapCount));
        map.addPolyline(new PolylineOptions()
                .addAll(routePoints)
                .color(colors[mapCount])
                .geodesic(true)
                .width(6));

        // check for wifi here else this will crash
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

    private void updateRouteDetails() {
        mSessionStartTimeTextView.setText("group start location");
        mSessionStopTimeTextView.setText("group stop location");
        mSessionDurationTextView.setText("group duration");
    }

    private String getRouteData(int mapCount) {
        return Constants.PATHS[mapCount];
    }
}