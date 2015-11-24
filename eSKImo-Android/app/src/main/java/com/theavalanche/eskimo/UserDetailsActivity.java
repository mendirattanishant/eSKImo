package com.theavalanche.eskimo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class UserDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        LinearLayout userListView = (LinearLayout) findViewById(R.id.llEvents);

        LayoutInflater inflater = LayoutInflater.from(this);
        for(int i = 0; i < 10; i++){
            View view =  inflater.inflate(R.layout.row_event, new LinearLayout(this));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(UserDetailsActivity.this, EventDetailsActivity.class);
                    startActivity(intent);
                }
            });
            userListView.addView(view);
        }
    }

}
