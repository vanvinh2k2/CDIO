package com.example.shope.model;

public class Notify {
    private String image, title, content;

    public Notify(String image, String title, String content) {
        this.image = image;
        this.title = title;
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
