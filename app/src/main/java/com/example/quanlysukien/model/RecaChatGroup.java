package com.example.quanlysukien.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecaChatGroup {
    @SerializedName("data")
    private ArrayList<ChatGroup> data;
    private String msg;
    private int status;

    public ArrayList<ChatGroup> getData() {
        return data;
    }

    public void setData(ArrayList<ChatGroup> data) {
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
