package com.example.quanlysukien.model;

import com.google.gson.annotations.SerializedName;

public class Group {
    @SerializedName("_")
    String id;
    String userID ,groupID, status,content;

    public Group(String userID, String groupID, String status, String content) {
        this.userID = userID;
        this.groupID = groupID;
        this.status = status;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Group() {
    }

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

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
