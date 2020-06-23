package com.example.faq_chatapp;

public class Answer {
    private int id;
    private int ans;
    private String description;
   private String author;

    public Answer(){

    }

    public Answer(int id,int ans, String description,String author) {
        this.id = id;
        this.ans = ans;
        this.description = description;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
