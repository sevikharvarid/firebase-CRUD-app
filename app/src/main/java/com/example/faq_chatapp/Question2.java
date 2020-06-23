package com.example.faq_chatapp;

public class Question2 {

    public Question2(String category, String title, String description) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.views=0;

    }

    public Question2() {

    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    private int views;
    private String category;
    private String title;
    private String description;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
