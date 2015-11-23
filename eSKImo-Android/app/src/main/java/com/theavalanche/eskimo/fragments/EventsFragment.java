package com.theavalanche.eskimo.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.theavalanche.eskimo.AddEventActivity;
import com.theavalanche.eskimo.EventDetailsActivity;
import com.theavalanche.eskimo.R;
import com.theavalanche.eskimo.adapters.EventsAdapter;
import com.theavalanche.eskimo.models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment{

    public static final String TAG = "EventsFragment";

    private ListView listView;
    private EventsAdapter adapter;

    public static final int REQUEST_ADD_EVENT = 1000;

    public EventsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, new LinearLayout(getActivity()));
        listView = (ListView) view.findViewById(R.id.lvEvents);
        adapter = new EventsAdapter(getActivity());
        listView.setAdapter(adapter);

        // Sample events list
        List<Event> events = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            Event event = new Event();
            events.add(event);
        }
        adapter.addEvents(events);

        setHasOptionsMenu(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_events, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_event) {
            Intent intent = new Intent(getActivity(), AddEventActivity.class);
            startActivityForResult(intent, REQUEST_ADD_EVENT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_ADD_EVENT:
                    // TODO Refresh event list since new event is added
                    return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
