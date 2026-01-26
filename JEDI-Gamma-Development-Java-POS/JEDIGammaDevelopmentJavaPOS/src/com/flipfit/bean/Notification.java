package com.flipfit.bean;

public class Notification {
    private int notificationId;
    private int userId;
    private String message;

    public Notification(int notificationId, int userId, String message) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
    }

    public void send() {
        System.out.println("[NOTIFICATION SENT to User " + userId + "]: " + message);
    }
}