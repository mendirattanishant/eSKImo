package com.theavalanche.eskimo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.theavalanche.eskimo.R;

public class TrackerFragment extends Fragment{

    public TrackerFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracker, new LinearLayout(getActivity()));
        return view;
    }
}
