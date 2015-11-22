package com.theavalanche.eskimo.maps;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by jue on 11/22/15.
 */
public class Route {
    private int userId;
    private String title;
    private LatLng startLoc;
    private LatLng endLoc;
    private Date startTime;
    private Date endTime;
    private String path;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(LatLng startLoc) {
        this.startLoc = startLoc;
    }

    public LatLng getEndLoc() {
        return endLoc;
    }

    public void setEndLoc(LatLng endLoc) {
        this.endLoc = endLoc;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // used to create json out of this model object for backend to consume.
    public String toJson(){
        return "";
    }
}
