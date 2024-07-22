package com.parsh.rrs;

public class Restaurant {
    private String name;
    private String categoryName;
    private double rating;
    private double distance;
    private String url;

    public Restaurant(String name, String categoryName, double rating, double distance, String url) {
        this.name = name;
        this.categoryName = categoryName;
        this.rating = rating;
        this.distance = distance;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getRating() {
        return rating;
    }

    public double getDistance() {
        return distance;
    }

    public String getUrl() {
        return url;
    }
}
