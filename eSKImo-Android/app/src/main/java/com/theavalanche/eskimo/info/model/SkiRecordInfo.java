package com.theavalanche.eskimo.info.model;

/**
 * Created by Christopher on 12/1/2015.
 */
public class SkiRecordInfo {
    int ski_id;
    int user_id;
    int event_id;
    String start_time;


    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getSki_id() {
        return ski_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setSki_id(int ski_id) {
        this.ski_id = ski_id;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
