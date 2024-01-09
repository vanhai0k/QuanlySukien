package com.example.quanlysukien.model;

public class JoinEventRequest {
    private String eventId;
    private String userId;
    private String status;

    public JoinEventRequest(String eventId, String userId, String status) {
        this.eventId = eventId;
        this.userId = userId;
        this.status = status;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
