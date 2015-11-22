package com.theavalanche.eskimo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class AddEventActivity extends ActionBarActivity {

    LinearLayout userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        userListView = (LinearLayout) findViewById(R.id.llUsers);

        //Sample code to add users to view
        List<View> userViews = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);
        for(int i = 0; i < 10; i++){
            View view =  inflater.inflate(R.layout.row_user, new LinearLayout(this));
            userViews.add(view);
            userListView.addView(view);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_save_event:
                // TODO Save event on server
                return true;
            case R.id.action_cancel:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
