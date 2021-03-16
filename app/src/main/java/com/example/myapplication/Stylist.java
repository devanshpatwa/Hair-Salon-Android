package com.example.myapplication;

public class Stylist {

    private int id;
    private String name;
    private String time1;
    private String services;
    private String about;
    private Float rating;
    private  String p_i;


    Stylist(int id, String name, String time1, String services, String about, Float rating,String p_i){
        this.id=id;
        this.name=name;
        this.time1=time1;
        this.services=services;
        this.rating=rating;
        this.about=about;
        this.p_i=p_i;
    }

    Stylist(){

    }

    public String getP_i() {
        return p_i;
    }

    public void setP_i(String p_i) {
        this.p_i = p_i;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
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

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}

