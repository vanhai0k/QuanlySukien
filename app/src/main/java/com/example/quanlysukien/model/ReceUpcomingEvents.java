package com.example.quanlysukien.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReceUpcomingEvents {

    @SerializedName("upcomingEvents")
    private ArrayList<EventChennal> upcomingEvents;
    private String msg;
    private int status;

    public ArrayList<EventChennal> getUpcomingEvents() {
        return upcomingEvents;
    }

    public void setUpcomingEvents(ArrayList<EventChennal> upcomingEvents) {
        this.upcomingEvents = upcomingEvents;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
