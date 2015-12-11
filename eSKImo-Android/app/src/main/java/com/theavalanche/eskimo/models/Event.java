package com.theavalanche.eskimo.models;

import java.util.List;

public class Event {

    private String user_id;
    private String event_id;
    private String event_details;
    private String event_name;
    private String location;
    private String start_time;
    private String end_time;
    private String flag;

    private List<User> users;

    public String getEvent_id() {
        return event_id;
    }

    public String getUser_id() {
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

    public void setEvent_id(String event_id) {
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

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
