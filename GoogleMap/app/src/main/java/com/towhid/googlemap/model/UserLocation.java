package com.towhid.googlemap.model;

public class UserLocation {

    private String userName;
    private double latitude;
    private double longitude;

    public UserLocation() {

    }

    public UserLocation(String userName, double latitude, double longitude) {
        this.userName = userName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
