package com.example.bradleychippi.water_test_kit;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class SensorTable extends AppCompatActivity {

    ListView list;
    ListItemAdapter listAdapter;
    ArrayList<ListModel> customList = new ArrayList<ListModel>();
    String[] location, info, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_table);

        //Somehow convert date information into a string array
        //This information will come from the firebase database
        //This is just an example will be changed:
        location = new String[]{"Saginaw", "Bay City", "Midland", "Flint", "Saginaw"};
        info = new String[]{"45ppm", "355ppm", "342ppm", "255ppm", "253ppm"};
        date = new String[]{"2/4/17", "2/7/17", "2/15/17", "2/20/17", "2/27/17"};

        for(int i=0; i<info.length; i++) {
            final ListModel item = new ListModel(info[i], date[i], location[i]);
            customList.add(item);
        }
        Log.e("Check", "ArraySize: " + customList.size());
        list = (ListView) findViewById(R.id.SensorDataTable);
        listAdapter = new ListItemAdapter(customList, this);
        list.setAdapter(listAdapter);
    }
}
