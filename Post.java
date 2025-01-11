package com.example.weatherapplication20;

import java.util.List;
import java.util.ArrayList;

public class Post {
    private String content;   // Post content (text)
    private String userId;    // ID of the user who created the post
    private String groupId;   // ID of the group the post belongs to
    private long timestamp;   // Timestamp of when the post was created
    private String mediaUrl;  // URL of any media (image/video) attached to the post
    private List<String> reactions;  // List of user IDs who reacted to the post
    private List<Comment> comments;   // List of comments on the post

    // Empty constructor required for Firebase Firestore
    public Post() {
        this.reactions = new ArrayList<>(); // Initialize reactions list
        this.comments = new ArrayList<>();  // Initialize comments list
    }

    // Constructor to initialize the post with content, media URL, and timestamp
    public Post(String groupId, String content, String mediaUrl, long timestamp) {
        this.groupId = groupId;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.timestamp = timestamp;
        this.reactions = new ArrayList<>(); // Initialize reactions list
        this.comments = new ArrayList<>();  // Initialize comments list
    }

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public List<String> getReactions() {
        return reactions;
    }

    public void setReactions(List<String> reactions) {
        this.reactions = reactions;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static class Comment {
        private String userId;
        private String content;
        private long timestamp;

        // Getters and setters for comment fields
        public Comment() {}

        public Comment(String userId, String content, long timestamp) {
            this.userId = userId;
            this.content = content;
            this.timestamp = timestamp;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
