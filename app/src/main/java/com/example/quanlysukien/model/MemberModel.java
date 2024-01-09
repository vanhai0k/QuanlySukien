package com.example.quanlysukien.model;

import com.google.gson.annotations.SerializedName;

public class MemberModel {
    @SerializedName("_id")
    String id;
    String userID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
