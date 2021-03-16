package com.example.myapplication;

public class StyleStudio {

    private int id;
    private String name;
    private String image;
    private String about;
    private int ratings;
    private double v;
    private double v1;


    StyleStudio(int id, String name, String image, String about,int ratings,double v,double v1){
        this.name=name;
        this.image=image;
        this.id=id;
        this.about=about;
        this.ratings=ratings;
        this.v=v;
        this.v1=v1;
    }

    StyleStudio(){

    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public double getV1() {
        return v1;
    }

    public void setV1(double v1) {
        this.v1 = v1;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
