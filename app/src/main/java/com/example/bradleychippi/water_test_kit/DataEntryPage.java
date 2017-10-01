package com.example.bradleychippi.water_test_kit;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DataEntryPage extends AppBaseActivity {

    EditText currentData, currentLocation;
    Button addToTable, viewData;

    private static final String TAG = "ActivityManager";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Sensors");
    List<String> sensors = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry_page);

        //This allows code to manipulate data
        viewData = (Button) findViewById(R.id.ViewData);
        addToTable = (Button) findViewById(R.id.SendToDB);
        currentData = (EditText) findViewById(R.id.CurrentData);
        currentLocation = (EditText) findViewById(R.id.Location);

        final Spinner sensorSpinner = (Spinner) findViewById(R.id.SensorSpinner);
        final ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, sensors);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sensorSpinner.setAdapter(adp1);

        //add all sensors to sensor select spinner
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    sensors.clear();
                    for(DataSnapshot sensorSnap : dataSnapshot.getChildren()){
                        //String sensorName = sensorSnap.child("Sensors").getValue(String.class);
                        sensors.add(sensorSnap.getKey());
                    }
                    sensorSpinner.setAdapter(adp1);}
                else{
                    sensorSpinner.setAdapter(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    public void ViewData(View v){
        Intent DataTable = new Intent(this, SensorTable.class);
        startActivity(DataTable);
    }


    public void onSubmit(View view){
        //Send data to firebase db and clear edit text values
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Sensors:
                Intent addSensor = new Intent(this, Sensors.class);
                startActivity(addSensor);
                return true;

            case R.id.Data_Entry:
                Intent dataEntry = new Intent(this, DataEntryPage.class);
                startActivity(dataEntry);
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

}
