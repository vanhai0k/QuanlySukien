package com.example.quanlysukien.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReceEvent {
    @SerializedName("data")
    private ArrayList<EventChennal> data;
    private String msg;
    private int status;

    public ArrayList<EventChennal> getData() {
        return data;
    }

    public void setData(ArrayList<EventChennal> data) {
        this.data = data;
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
