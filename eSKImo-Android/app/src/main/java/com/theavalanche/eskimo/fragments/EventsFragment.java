package com.theavalanche.eskimo.fragments;

import android.app.Activity;
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
import android.widget.Toast;

import com.theavalanche.eskimo.AddEventActivity;
import com.theavalanche.eskimo.EventDetailsActivity;
import com.theavalanche.eskimo.R;
import com.theavalanche.eskimo.Session;
import com.theavalanche.eskimo.adapters.EventsAdapter;
import com.theavalanche.eskimo.info.api.EventRESTClient;
import com.theavalanche.eskimo.models.Event;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class EventsFragment extends Fragment{

    public static final String TAG = "EventsFragment";

    private ListView listView;
    private EventsAdapter adapter;
    private EventRESTClient eventRESTClient;

    public static final int REQUEST_ADD_EVENT = 1000;

    public EventsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventRESTClient = new EventRESTClient();
        adapter = new EventsAdapter(getActivity());

        eventRESTClient.getAttendingEvents(Session.loggedUser.getId()).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Response<List<Event>> response, Retrofit retrofit) {
                Log.d(TAG, "Got events" + response.body().size());
                adapter.addEvents(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "Error getting my events");
                t.printStackTrace();
                Toast.makeText(getActivity(), "Error getting my events", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, new LinearLayout(getActivity()));
        listView = (ListView) view.findViewById(R.id.lvEvents);

        listView.setAdapter(adapter);

        setHasOptionsMenu(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                intent.putExtra("eventId", adapter.getEvent(i).getEvent_id());
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
        int id = item.getItemId();

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
