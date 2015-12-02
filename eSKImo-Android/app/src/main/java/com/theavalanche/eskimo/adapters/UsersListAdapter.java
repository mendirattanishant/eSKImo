package com.theavalanche.eskimo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.theavalanche.eskimo.R;
import com.theavalanche.eskimo.models.User;

import java.util.ArrayList;
import java.util.List;

public class UsersListAdapter extends BaseAdapter {


    private List<User> users;
    private LayoutInflater inflater;
    private Context context;

    public UsersListAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
        users = new ArrayList<>();
    }

    public void addUsers(List<User> users){
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        User user = users.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_user, new LinearLayout(context));
            viewHolder = new ViewHolder();

            viewHolder.dp = (ImageView) convertView.findViewById(R.id.ivUser);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tagline = (TextView) convertView.findViewById(R.id.tvTagline);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(user.name);
        viewHolder.tagline.setText(user.tagline);
        Picasso.with(context).load(user.dpUrl).placeholder(R.drawable.user_placeholder).into(viewHolder.dp);

        return convertView;
    }

    private static class ViewHolder{
        ImageView dp;
        TextView name, tagline;
    }

}