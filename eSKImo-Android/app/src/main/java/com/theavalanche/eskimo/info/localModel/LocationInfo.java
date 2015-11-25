package com.theavalanche.eskimo.info.localModel;

/**
 * Created by Christopher on 11/24/2015.
 */
public class LocationInfo{
    int locationID;
    int eventID;
    long lat;
    long lon;

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLat() {
        return lat;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public long getLon() {
        return lon;
    }
}
