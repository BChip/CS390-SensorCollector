package com.example.bradleychippi.water_test_kit;

/**
 * Created by waqas on 10/29/2017.
 */

public class SensorData {

    String data, date, location;

    public SensorData(){
        //required for using dataSnapshot.getValue()
    }

    public SensorData(String Data, String Date, String Location){
        this.data = Data;
        this.date = Date;
        this.location = Location;
    }

    public String getLoc(){
        return this.location;
    }
    public String getInfo(){
        return this.data;
    }
    public String getDate(){
        return this.date;
    }

    public void setInfo(String data){this.data = data;}
    public void setDate(String date){this.date = date;}
    public void setLoc(String location){this.location = location;}

    @Override
    public String toString() {
        return ("Data: " + data + "Date: " + date + "Location: " + location);
    }
}
