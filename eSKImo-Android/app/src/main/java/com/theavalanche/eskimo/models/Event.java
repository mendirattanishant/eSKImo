package com.theavalanche.eskimo.models;

public class Event {

    private int user_id;
    private int event_id;
    private String event_details;
    private String event_name;
    private String location;
    private String start_time;
    private String end_time;

    public int getEvent_id() {
        return event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getEvent_details() {
        return event_details;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getLocation() {
        return location;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setEvent_details(String event_details) {
        this.event_details = event_details;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
