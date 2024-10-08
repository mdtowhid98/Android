package com.towhid.androidappanimation2.model;

public class NotificationModel {

    private int id;
    private String date;
    private String news;

    public NotificationModel() {}

    public NotificationModel(int id, String news, String date) {
        this.id = id;
        this.news = news;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
