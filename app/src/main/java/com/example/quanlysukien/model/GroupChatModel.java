package com.example.quanlysukien.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupChatModel {
    @SerializedName("_id")
    String id;

    String name,admin;
    List<NumberUser> numbers;
    public static class NumberUser{
        private String userID;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }
    }
    String eventID;
    List<ChatHistirys> chatHistory;
    String image;

    public static class ChatHistirys{
        String userId,message,image,time;
        String chatId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getChatId() {
            return chatId;
        }

        public void setChatId(String chatId) {
            this.chatId = chatId;
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

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public List<NumberUser> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<NumberUser> numbers) {
        this.numbers = numbers;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public List<ChatHistirys> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(List<ChatHistirys> chatHistory) {
        this.chatHistory = chatHistory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
