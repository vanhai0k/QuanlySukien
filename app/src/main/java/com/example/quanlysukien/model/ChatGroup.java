package com.example.quanlysukien.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatGroup {
    @SerializedName("_id")
    String id;

    String name;
    List<UserNumber> numbers;
    String admin,eventID;
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static class UserNumber {
        private UserEvent user_id;

        public UserEvent getUser_id() {
            return user_id;
        }

        public void setUser_id(UserEvent user_id) {
            this.user_id = user_id;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserNumber> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<UserNumber> numbers) {
        this.numbers = numbers;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}
