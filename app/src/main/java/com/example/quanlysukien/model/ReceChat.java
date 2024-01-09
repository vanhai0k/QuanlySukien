package com.example.quanlysukien.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReceChat {
    @SerializedName("data")
    private ArrayList<ChatModel> data;
    private String msg;
    private int status;

    public ArrayList<ChatModel> getData() {
        return data;
    }

    public void setData(ArrayList<ChatModel> data) {
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
