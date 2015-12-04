package com.theavalanche.eskimo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theavalanche.eskimo.info.api.EventRESTClient;
import com.theavalanche.eskimo.models.Event;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class UserDetailsActivity extends ActionBarActivity {

    public static final String TAG = "UserDetailsActivity";

    private TextView tvName;
    private TextView tvtag;
    private ImageView dp;

    private EventRESTClient eventRESTClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        eventRESTClient = new EventRESTClient();

        final LinearLayout userListView = (LinearLayout) findViewById(R.id.llEvents);
        final LayoutInflater inflater = LayoutInflater.from(this);

        tvName = (TextView) findViewById(R.id.tvUserName);
        tvtag = (TextView) findViewById(R.id.tvTagline);

        dp = (ImageView) findViewById(R.id.ivUser);

        setTitle(getIntent().getStringExtra("name"));

        tvName.setText(getIntent().getStringExtra("name"));
        tvtag.setText(getIntent().getStringExtra("tag"));
        String dpUrl = getIntent().getStringExtra("dp");
        if(dpUrl != null && dpUrl.length() != 0){
            Picasso
                    .with(UserDetailsActivity.this)
                    .load(dpUrl)
                    .placeholder(R.drawable.user_placeholder)
                    .into(dp);
        }

        eventRESTClient.getAttendingEvents(getIntent().getStringExtra("id")).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Response<List<Event>> response, Retrofit retrofit) {
                if(response.body() != null){
//                    Log.d(TAG, "Got events" + response.body().size());
                    if(response.body() != null){
                        List<Event> events = response.body();

                        for(int i = 0; i < events.size(); i++){
                            View view =  inflater.inflate(R.layout.row_event, new LinearLayout(UserDetailsActivity.this));
                            Event event = events.get(i);
                            ((TextView) view.findViewById(R.id.tvTitle)).setText(event.getEvent_name());
                            ((TextView) view.findViewById(R.id.tvDesc)).setText(event.getEvent_details());
                            ((TextView) view.findViewById(R.id.tvStartTime)).setText(event.getStart_time());
                            ((TextView) view.findViewById(R.id.tvEndTime)).setText(event.getEnd_time());
//                            view.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent intent = new Intent(UserDetailsActivity.this, EventDetailsActivity.class);
//                                    startActivity(intent);
//                                }
//                            });
                            userListView.addView(view);
                        }
                    }

                }else{
                    Toast.makeText(UserDetailsActivity.this, "No events found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Error getting my events");
                t.printStackTrace();
                Toast.makeText(UserDetailsActivity.this, "Error getting my events", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
