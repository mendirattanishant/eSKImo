package com.theavalanche.eskimo.models;

import com.google.android.gms.maps.model.LatLng;

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
        return "";
    }
}
