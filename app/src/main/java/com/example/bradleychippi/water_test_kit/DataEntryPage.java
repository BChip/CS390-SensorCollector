// App: Sensor Collector
// Author: Waqas Qureshi
// Course: CS390
// Instructor: Dr. Corser

package com.example.bradleychippi.water_test_kit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This activity class is the main data entry page.
 * At the start it displays a city location.
 * After data is entered, the submit button sends
 * the data to a firebase database.
 * View Data opens the Sensor Table activity.
 * @author Waqas Qureshi
 * */

public class DataEntryPage extends AppBaseActivity {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private FusedLocationProviderClient mFusedLocationClient;

    EditText currentData, currentLocation, noteText;
    Button addToTable, viewData;
    //final Spinner sensorSpinner = (Spinner) findViewById(R.id.SensorSpinner);

    private static final String TAG = "ActivityManager";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Sensors");
    List<String> sensors = new ArrayList<String>();

    /**
     * onCreate contains the start up information
     * including city name, and displays the
     * entry lines and submit and table buttons.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry_page);

        //This allows code to manipulate data
        viewData = (Button) findViewById(R.id.ViewData);
        addToTable = (Button) findViewById(R.id.SendToDB);
        currentData = (EditText) findViewById(R.id.CurrentData);
        currentLocation = (EditText) findViewById(R.id.Location);
        noteText = (EditText) findViewById(R.id.Note);

        double latitude, longitude = 0.0;

        //populate location edit text
        String currentCity = "Location";

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            //If you are trying to emulate this app, comment out everything until....
            Loc location = new Loc(this);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            if(latitude + longitude != 0.0){
                currentCity = cityLocation(latitude,longitude);
            }
            //this
        }
        if(currentCity == "Location")
            currentLocation.setHint(currentCity);
        else
            currentLocation.setText(currentCity);

        //Populate spinner with sensor list
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

    /**
     * onStart the app requests location permissions.
     * */
    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        }
    }

    /**
     * ViewData takes the sensor list and
     * opens the SensorTable activity.
     * */
    //Change activities
    public void ViewData(View v){
        Intent DataTable = new Intent(this, SensorTable.class);
        Log.e("LIST", "ViewData: " + sensors );
        DataTable.putStringArrayListExtra("SENSORLIST", (ArrayList<String>) sensors);
        startActivity(DataTable);
    }

    /**
     * onSubmit packages and sends the data to the firebase database
     * and displays a toast message.
     * */
    public void onSubmit(View view){
        //Send data to firebase db and clear edit text values
        Spinner sensor = (Spinner) findViewById(R.id.SensorSpinner);
        String sensorName = sensor.getSelectedItem().toString();
        String data = currentData.getText().toString();
        String loc = currentLocation.getText().toString();
        String note = noteText.getText().toString();
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(currentDate);
        Log.d("Check Date", "onSubmit: " + formattedDate);
        //push data to firebase
        Map<String, String> userData = new HashMap<String, String>();
        userData.put("Data", data);
        userData.put("Date", formattedDate);
        userData.put("Location", loc);
        userData.put("Note", note);
        DatabaseReference dRef = database.getReference("Sensors");
        dRef.child(sensorName).push().setValue(userData);
        //clear values
        currentData.setText("");
        noteText.setText("");
        Toast.makeText(getApplicationContext(), "Added New Data to Sensor " + sensorName, Toast.LENGTH_SHORT).show();
    }

    /**
     * This section is dedicated to determining location data*/
    //Check for location permissions
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(DataEntryPage.this,
                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }
    //Display request
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION);
        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    //Get city name
    public String cityLocation (double lat, double lon){
        String city = "";
        //Codes data into an address list
        Geocoder geocoder = new Geocoder(DataEntryPage.this, Locale.getDefault());
        List<Address> addressList;
        try{
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if(addressList.size() > 0){
                city = addressList.get(0).getLocality();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }



}
