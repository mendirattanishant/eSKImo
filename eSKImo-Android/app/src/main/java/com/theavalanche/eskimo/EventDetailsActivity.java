package com.theavalanche.eskimo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.theavalanche.eskimo.info.api.EventRESTClient;
import com.theavalanche.eskimo.models.Event;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EventDetailsActivity extends FragmentActivity {

    public static final String TAG = "EventDetailsActivity";

    private String eventId;

    private TextView tvEventTitle;
    private TextView tvEventDesc;
    private TextView tvEventStart;
    private TextView tvEventEnd;

    private LinearLayout userListView;

    private EventRESTClient eventRESTClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        eventId = getIntent().getStringExtra("eventId");

        if(eventId == null){
            Toast.makeText(this, "Invalid Event!", Toast.LENGTH_SHORT).show();
            finish();
        }

        tvEventTitle = (TextView) findViewById(R.id.tvEventTitle);
        tvEventDesc = (TextView) findViewById(R.id.tvEventDesc);
        tvEventStart = (TextView) findViewById(R.id.tvStartTime);
        tvEventEnd = (TextView) findViewById(R.id.tvEndTime);

        userListView = (LinearLayout) findViewById(R.id.llEventUsers);

        eventRESTClient = new EventRESTClient();

        eventRESTClient.getEventById(eventId).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Response<Event> response, Retrofit retrofit) {
                Log.d(TAG, "Got event details.");
                Log.d(TAG, response.toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Problem getting the event");
                t.printStackTrace();;
            }
        });

        LayoutInflater inflater = LayoutInflater.from(this);
        for(int i = 0; i < 10; i++){
            View view =  inflater.inflate(R.layout.row_user, new LinearLayout(this));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(EventDetailsActivity.this, UserDetailsActivity.class);
                    startActivity(intent);
                }
            });
            userListView.addView(view);
        }
    }

}
