package com.theavalanche.eskimo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.theavalanche.eskimo.R;
import com.theavalanche.eskimo.models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends BaseAdapter{


    private List<Event> events;
    private LayoutInflater inflater;
    private Context context;

    public EventsAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
        events = new ArrayList<>();
    }

    public void addEvents(List<Event> events){
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        Event event = events.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_event, new LinearLayout(context));
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.tvDesc);
            viewHolder.startTime = (TextView) convertView.findViewById(R.id.tvStartTime);
            viewHolder.endTime = (TextView) convertView.findViewById(R.id.tvEndTime);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(event.getEvent_name());
        viewHolder.desc.setText(event.getEvent_details());
        viewHolder.startTime.setText(event.getStart_time());
        viewHolder.endTime.setText(event.getEnd_time());

        return convertView;
    }

    private static class ViewHolder{
        TextView title, desc, startTime, endTime;
    }

}
