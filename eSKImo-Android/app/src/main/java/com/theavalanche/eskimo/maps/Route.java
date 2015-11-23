package com.theavalanche.eskimo.maps;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jue on 11/22/15.
 */
public class Route {
    private int userId;
    private String title;
    private Location startLocation;
    private Location endLocation;
    private Date startTime;
    private Date endTime;

    private String path;
    private List<Location> locations = new ArrayList<Location>();
    List<LatLng> latLngs = new ArrayList<LatLng>();

    public List<LatLng> getLatLngs() {
        return latLngs;
    }

    public void addLocaiton(Location location) {
        locations.add(location);
        latLngs.add(new LatLng(location.getLatitude(), location.getLongitude()));
    }

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

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
        // clearing the locations list as this should be called for every fresh route.
        locations = new ArrayList<Location>();
        endLocation = null;
        addLocaiton(startLocation);
    }

    public Location getEndLocation() {
        if(locations == null || locations.size()<=0){
            return null;
        }
        return locations.get(locations.size()-1);
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
        if(path==null && latLngs!=null){
            path = PolyUtil.encode(latLngs);
        }
        return path;
    }

    public void setPath(String path) {

        this.path = path;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    // used to create json out of this model object for backend to consume.
    public String toJson() {
        return "";
    }
}
