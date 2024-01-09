package com.example.quanlysukien.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventChennal {
    @SerializedName("_id")
    private String id;
    private String eventName;
    private String location;
    private String description;
    private String startTime;
    private String endTime;
    private String user_id;
    private List<ParticipantModel> participants;
    private String createdAt;
    private String updatedAt;
    private String status;
    private String limit;


    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    public static class ParticipantModel {
//        private String user_id;
//        private String status;
//
//        // Constructors, getters, setters, etc.
//
//        public String getUser_id() {
//            return user_id;
//        }
//
//        public void setUser_id(String user_id) {
//            this.user_id = user_id;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//    }
    public static class ParticipantModel {
        private String status;
        private UserEvent user_id;

    public UserEvent getUser_id() {
        return user_id;
    }

    public void setUser_id(UserEvent user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<ParticipantModel> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantModel> participants) {
        this.participants = participants;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
