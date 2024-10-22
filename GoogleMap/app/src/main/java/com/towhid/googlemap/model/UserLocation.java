package com.towhid.googlemap.model;

public class UserLocation {

    private String username;
    private double latitude;
    private double longitude;

    public UserLocation() {}

    public UserLocation(String username, double latitude, double longitude) {
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }




}