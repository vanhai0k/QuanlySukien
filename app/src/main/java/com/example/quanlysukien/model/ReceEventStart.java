package com.example.quanlysukien.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReceEventStart {
    @SerializedName("ongoingEventsCount")
    private ArrayList<EventChennal> ongoingEventsCount;
    private String msg;
    private int status;

    public ArrayList<EventChennal> getOngoingEventsCount() {
        return ongoingEventsCount;
    }

    public void setOngoingEventsCount(ArrayList<EventChennal> ongoingEventsCount) {
        this.ongoingEventsCount = ongoingEventsCount;
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
