package com.theavalanche.eskimo.info.model;

/**
 * Created by Christopher on 12/1/2015.
 */
public class EventAttendeesInfo {
    int user_id;
    int event_id;

    public int getEvent_id() {
        return event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }
}
