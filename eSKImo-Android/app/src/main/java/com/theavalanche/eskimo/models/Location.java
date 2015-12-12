package com.theavalanche.eskimo.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

/**
 * Created by jue on 11/22/15.
 */
public class Location {
    private double latitude;
    private double longitude;
    private double altitude;

    public Location(android.location.Location loc){
        this.latitude =  loc.getLatitude();
        this.longitude = loc.getLongitude();
        this.altitude = loc.getAltitude();
    }

    public Location(double latitude, double longitude,double altitude){
        this.latitude =  latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public LatLng getLatLng(){
        return new LatLng(this.latitude,this.longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    // used to create json out of this model object for backend to consume.
    public String toJson(){
        Gson gson = new Gson();
        return  gson.toJson(this);
    }

    public void fromJson(String location){
        Gson gson = new Gson();
        Location location1 = gson.fromJson(location,this.getClass());
        this.latitude =  location1.getLatitude();
        this.longitude =  location1.getLongitude();
        this.altitude = location1.getAltitude();
    }
}