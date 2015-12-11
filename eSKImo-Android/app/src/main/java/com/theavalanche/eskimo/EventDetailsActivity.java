package com.theavalanche.eskimo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theavalanche.eskimo.adapters.UsersListAdapter;
import com.theavalanche.eskimo.info.api.EventRESTClient;
import com.theavalanche.eskimo.info.api.UserRESTClient;
import com.theavalanche.eskimo.info.model.EventAttendeesInfo;
import com.theavalanche.eskimo.models.Event;
import com.theavalanche.eskimo.models.User;

import java.util.ArrayList;
import java.util.List;

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

    private Button bInviteUser;

    private LinearLayout userListView;

    private EventRESTClient eventRESTClient;
    private UserRESTClient userRESTClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        eventRESTClient = new EventRESTClient();
        userRESTClient = new UserRESTClient();

        eventId = getIntent().getStringExtra("eventId");

        if(eventId == null){
            Toast.makeText(this, "Invalid Event!", Toast.LENGTH_SHORT).show();
            finish();
        }

        String flag = getIntent().getStringExtra("flag");

        if(flag != null && !flag.equals("YES")){
            new AlertDialog.Builder(this)
                    .setTitle("Confirm")
                    .setMessage("Are you going to this event?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, int which) {
                            Log.d(TAG, "Going...");
                            userRESTClient.goToEvent(eventId, Session.loggedUser.getId()).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Response<String> response, Retrofit retrofit) {
                                    Log.d(TAG, "Going registered");
                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailure(Throwable t) {

                                }
                            });
                        }
                    })
                    .setNegativeButton("Undecided", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        tvEventTitle = (TextView) findViewById(R.id.tvEventTitle);
        tvEventDesc = (TextView) findViewById(R.id.tvEventDesc);
        tvEventStart = (TextView) findViewById(R.id.tvStartTime);
        tvEventEnd = (TextView) findViewById(R.id.tvEndTime);

        userListView = (LinearLayout) findViewById(R.id.llEventUsers);

        bInviteUser = (Button) findViewById(R.id.bInviteUser);

        getEventDetails();


        bInviteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeUserPicker();
            }
        });

    }


    private void makeUserPicker(){
        final Dialog userPickerDialog = new Dialog(this);

        View view = getLayoutInflater().inflate(R.layout.picker_user, null);

        final ListView lv = (ListView) view.findViewById(R.id.lvUsers);

        final UsersListAdapter adapter = new UsersListAdapter(this);

        userRESTClient.getUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit) {
                if(response.body() != null){
                    adapter.addUsers(response.body());
                    lv.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(EventDetailsActivity.this, "Problem getting users", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = adapter.getUser(i);
                userRESTClient.attendEvent(eventId, user.getId()).enqueue(new Callback<EventAttendeesInfo>() {
                    @Override
                    public void onResponse(Response<EventAttendeesInfo> response, Retrofit retrofit) {
                        getEventDetails();
                        userPickerDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(EventDetailsActivity.this, "Problem inviting user", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        });

        userPickerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        userPickerDialog.setContentView(view);
        userPickerDialog.show();

    }

    private void getEventDetails(){
        final LayoutInflater inflater = LayoutInflater.from(this);

        eventRESTClient.getEventById(eventId).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Response<Event> response, Retrofit retrofit) {
                Log.d(TAG, "Got event details.");
                Log.d(TAG, ""+response.body());
                Event event = response.body();
                tvEventTitle.setText(""+event.getEvent_name());
                tvEventDesc.setText(""+event.getEvent_details());
                tvEventStart.setText("Starts on: "+event.getStart_time());
                tvEventEnd.setText("Ends on: "+event.getEnd_time());
                userListView.removeAllViews();
                // TODO Support location and user list
                for(int i = 0; i < event.getUsers().size(); i++){
                    final User user = event.getUsers().get(i);
                    View view =  inflater.inflate(R.layout.row_user, new LinearLayout(EventDetailsActivity.this));

                    TextView tvUserName = (TextView) view.findViewById(R.id.tvUserName);
                    TextView tvTagline = (TextView) view.findViewById(R.id.tvTagline);
                    tvUserName.setText(user.getName());
                    tvTagline.setText(user.getTagline());

                    ImageView dp = (ImageView) view.findViewById(R.id.ivUser);
                    if(user.getDpUrl() != null && user.getDpUrl().length() != 0){
                        Picasso
                                .with(EventDetailsActivity.this)
                                .load(user.getDpUrl())
                                .placeholder(R.drawable.user_placeholder)
                                .into(dp);
                    }


                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(EventDetailsActivity.this, UserDetailsActivity.class);
                            intent.putExtra("id", user.getId());
                            intent.putExtra("name", user.getName());
                            intent.putExtra("tag", user.getTagline());
                            intent.putExtra("dp", user.getDpUrl());
                            startActivity(intent);
                        }
                    });
                    userListView.addView(view);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Problem getting the event");
                t.printStackTrace();
            }
        });
    }

}
