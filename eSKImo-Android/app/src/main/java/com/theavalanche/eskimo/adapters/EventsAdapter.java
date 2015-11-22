package com.theavalanche.eskimo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_event, new LinearLayout(context));
            viewHolder = new ViewHolder();
//            viewHolder.name = (TextView) convertView.findViewById(R.id.tvVideoTitle);
//            viewHolder.desc = (TextView) convertView.findViewById(R.id.tvVideoDesc);
//            viewHolder.thumbail = (ImageView) convertView.findViewById(R.id.ivVideoThumbnail);
//            viewHolder.rlayout = (RelativeLayout) convertView.findViewById(R.id.RlayoutId);
//            viewHolder.favorite = (ImageButton) convertView.findViewById(R.id.favorite);

            convertView.setTag(viewHolder);
//            convertView.setTag(R.id.tvVideoTitle, viewHolder.name);
//            convertView.setTag(R.id.tvVideoDesc, viewHolder.desc);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.name.setText(videos.get(position).getName());
//        viewHolder.desc.setText(videos.get(position).getDesc());
//        Picasso.with(context).load(videos.get(position).getThumbnail()).into(viewHolder.thumbail);

        return convertView;
    }

    private static class ViewHolder{

    }

}
