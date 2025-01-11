package com.example.weatherapplication20;

public class Group {
    private String name;
    private int activeUsers;
    private int logoResId;

    public Group(String name, int activeUsers, int logoResId) {
        this.name = name;
        this.activeUsers = activeUsers;
        this.logoResId = logoResId;
    }

    public String getName() {
        return name;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public int getLogoResId() {
        return logoResId;
    }
}
