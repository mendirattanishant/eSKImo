package com.theavalanche.eskimo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class EventDetailsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        LinearLayout userListView = (LinearLayout) findViewById(R.id.llEventUsers);

        LayoutInflater inflater = LayoutInflater.from(this);
        for(int i = 0; i < 10; i++){
            View view =  inflater.inflate(R.layout.row_user, new LinearLayout(this));
            userListView.addView(view);
        }
    }

}
