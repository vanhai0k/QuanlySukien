package com.example.quanlysukien.model;

import com.google.gson.annotations.SerializedName;

public class JoinGroup {
    @SerializedName("_id")
    String id;
    UserEvent userID;
    String  groupID, status,content;

    public JoinGroup(UserEvent userID, String groupID, String status, String content) {
        this.userID = userID;
        this.groupID = groupID;
        this.status = status;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserEvent getUserID() {
        return userID;
    }

    public void setUserID(UserEvent userID) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
