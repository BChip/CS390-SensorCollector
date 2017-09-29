// App: Sensor Collector
// Author: Bradley Chippi
// Course: CS390
// Instructor: Dr. Corser

package com.example.bradleychippi.water_test_kit;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Sensors extends AppBaseActivity {

    private static final String TAG = "ActivityManager";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Sensors");
    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    list.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        list.add(data.getKey());
                    }
                    spinner.setAdapter(adp1);
                }else{
                    spinner.setAdapter(null);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    public void sendToFireBase(View v) {
        EditText edit_text = (EditText) findViewById(R.id.editText);
        String sensorToAdd = edit_text.getText().toString().toUpperCase();
        Date currentTime = Calendar.getInstance().getTime();
        DatabaseReference myRef = database.getReference("Sensors");
        myRef.child(sensorToAdd).setValue("");
        edit_text.setText("");
        Toast.makeText(getApplicationContext(), "ADDED: "+sensorToAdd, Toast.LENGTH_SHORT).show();
    }

    public void deleteFromFirebase(View v){
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String sensor = spinner.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(), "DELETED: "+sensor, Toast.LENGTH_SHORT).show();
        myRef.child(sensor).removeValue();
    }


}
