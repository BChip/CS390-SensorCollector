package com.example.bradleychippi.water_test_kit;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class DataEntryPage extends AppBaseActivity {

    EditText currentData, currentLocation;
    Button addToTable, viewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry_page);

        //This allows code to manipulate data
        viewData = (Button) findViewById(R.id.ViewData);
        addToTable = (Button) findViewById(R.id.SendToDB);
        currentData = (EditText) findViewById(R.id.CurrentData);
        currentLocation = (EditText) findViewById(R.id.Location);

    }

    public void ViewData(View v){
        Intent DataTable = new Intent(this, SensorTable.class);
        startActivity(DataTable);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.Add_Sensor:
//                Intent addSensor = new Intent(this, AddSensor.class);
//                startActivity(addSensor);
//                return true;
//
//            case R.id.Data_Entry:
//                Intent dataEntry = new Intent(this, DataEntryPage.class);
//                startActivity(dataEntry);
//                return true;
//
//            default:
//
//                return super.onOptionsItemSelected(item);
//
//        }
//    }

}
