// App: Sensor Collector
// Author: Waqas Qureshi
// Course: CS390
// Instructor: Dr. Corser

package com.example.bradleychippi.water_test_kit;

/**
 * This class is used to hold the information for the sensor table
 * @author Waqas Qureshi
 */

public class ListModel {
    private String data, dateTime, location, note;

    public ListModel(String data, String dateTime, String location, String note){
        this.data = data;
        this.dateTime = dateTime;
        this.location = location;
        this.note = note;
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
    public String getNote() {return note; }
}
