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

public class RouteDetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    protected TextView mSessionStartTimeTextView;
    protected TextView mSessionStopTimeTextView;
    protected TextView mSessionDurationTextView;
    protected Button mExitButton;

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
        Toast.makeText(this, "Goto profile details", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,RouteActivity.class);
        startActivity(i);
        finish();
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
        mSessionStartTimeTextView.setText("session start location");
        mSessionStopTimeTextView.setText("session stop location");
        mSessionDurationTextView.setText("session duration");
    }

    private String getRouteData(){
        return "ktwnE|}ypUYd@i@fAlAfApAhAy@|BwAfE{BnG_ArCEh@Y`Au@vBGPIXwBlG}B~Gm@tAw@xBuAfEsDpKi@tA{AjEmAlDg@rAGj@@~A?fA?dBc@Ku@EyG?uBBwA@kE?{@DaB@uA@AnC?fE@pF@tN@vEIxAIx@YpAEv@?~D@|L@vNBhL@zK?nEAxAAjI?xEG`FBbI?tEAjE@jE@jBLfJDjGFdBCjAEp@Mt@yAnGyC`LuAbF[tAy@bDOdBIfC_AhOWfEu@|DkB~JyBbLw@dEC`B@`CAbDKpPWvZGhMGfDKdNe@bx@EhIArAMbBc@bEsAtN_@tGs@tMWzE[fFyA`Wi@jJHzBHvCDdD@rK@jLBdTC|Q?bFAlAvA?|RF`BJ@pD?tK@jEAlE?pE@|DH~BV`DZtCr@jDhBfInAhFpA~Fz@|Dr@nCVx@`AxBvErJdClFhB|D?LJBnB~D|@lBrG|M~CxGpEfJbCxE|@pBvD~HfBnDdBvDfChF`DzHfCzGh@zAr@dBjBdFvBbGvAvDxEfMrJvVhAvCBFd@vA`AbC`CbGpA~DdAzBhDzGhEvK~L`[lDbJd@tAd@nABb@O\\[fAgBrFtAn@`@d@NZPz@VxA\\tBX`BRf@JJRpBn@fFn@hFz@fGp@jE`@~B~BvM~AjJtBvLnCvOhAdGPv@XrA^jAzAlDr@xAvCrElF`HdHfJ`IdKX`@BTBL^p@`@n@Tf@f@`BJj@N|AJx@N\\NV@Lf@h@~AhCd@r@FJq@z@aCrCoEtFoDpEW^d@r@z@fAt@~@fA|AnAzAnBhC|BtCbAxAvCkD`EcF|CuDVYpDuExFaH`BoBj@[l@bAh@z@p@dAtC|EdCzDnArBbA|A{DrDeJvJgAlAgBlB}A`BETBXf@t@hAzADJcBlB{B`CKOa@m@SYtC}CDG";
    }
}