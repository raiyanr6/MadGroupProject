package com.example.appfin;

public class Note {

    private String title, content;

    public Note(){

    }

    public String getContent() {

        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Note(String title, String content){
        this.content = content;
        this.title = title;
    }
}
