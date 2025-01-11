package com.example.weatherapplication20;

public class Comment {
    private String userId;
    private String userName;
    private String content;

    // Constructor
    public Comment(String userId, String userName, String content) {
        this.userId = userId;
        this.userName = userName;
        this.content = content;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

