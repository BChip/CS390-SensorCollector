package com.example.bradleychippi.water_test_kit;

/**
 * Created by waqas on 9/3/2017.
 */

public class ListModel {
    private String data;
    private String dateTime;
    private String location;

    public ListModel(String data, String dateTime, String location){
        this.data = data;
        this.dateTime = dateTime;
        this.location = location;
    }
    public String getData(){
        return data;
    }
    public String getDateTime(){
        return dateTime;
    }
    public String getLocation(){
        return location;
    }
}
