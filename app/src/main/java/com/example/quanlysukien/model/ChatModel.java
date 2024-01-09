package com.example.quanlysukien.model;

import com.google.gson.annotations.SerializedName;

public class ChatModel {

    @SerializedName("_id")
    String id;
    String user_id,user_event,content,image;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_event() {
        return user_event;
    }

    public void setUser_event(String user_event) {
        this.user_event = user_event;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
