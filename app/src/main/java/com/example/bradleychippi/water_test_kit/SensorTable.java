// App: Sensor Collector
// Author: Waqas Qureshi
// Course: CS390
// Instructor: Dr. Corser

package com.example.bradleychippi.water_test_kit;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.bradleychippi.water_test_kit.R.id.parent;

/**
 * @author Waqas Qureshi
 * */

public class SensorTable extends AppBaseActivity {

    ListView list;
    ListItemAdapter listAdapter;
    ArrayList<ListModel> customList = new ArrayList<ListModel>();
    //String[] location, info, date;
    //List<String> location, info, date = new ArrayList<>();
    List<SensorData> listData = new ArrayList<>();
    //Spinner tableSpinner;
    List<String> sensors = new ArrayList<String>();

    //List<HashMap<String, String>> testData = new ArrayList<>();


    private static final String TAG = "ActivityManager";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Sensors");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_table);
        Intent intent = getIntent();
        sensors = intent.getStringArrayListExtra("SENSORLIST");
        Log.e("PASSED", "onCreate: " + sensors );
        //populate spinner with sensor names
        final Spinner tableSpinner = (Spinner) findViewById(R.id.TableSpinner);
        final ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, sensors);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tableSpinner.setAdapter(adp1);

        list = (ListView) findViewById(R.id.SensorDataTable);
        listAdapter = new ListItemAdapter(customList, this);
        list.setAdapter(listAdapter);

//        final Map<String, String> itemData = new HashMap<String, String>();

        //set spinner change listener
        tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id){
                //populate list view with sensor specific data
                String sensorName = tableSpinner.getSelectedItem().toString();
                //final DatabaseReference nameRef = database.getReference(sensorName);  //Issue Here!
                final DatabaseReference nameRef = myRef.child(sensorName);
                Log.e("CHECK KEY", "onItemSelected: " + nameRef.getKey() );

                nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e("INFO: ", "onDataChange: " + dataSnapshot );
                        if(dataSnapshot.exists()){
                            listData.clear();
                            customList.clear();
                            for(DataSnapshot infoSnap : dataSnapshot.getChildren()){
                                Log.e("INFOSNAP", "onDataChange: " + infoSnap.getValue());
                                final Map<String, String> itemData = (HashMap<String, String>) infoSnap.getValue();
                                SensorData sData = new SensorData(itemData.get("Data"), itemData.get("Date"), itemData.get("Location"), itemData.get("Note"));
                                //Log.e("ITEM", "onDataChange: " + itemData.get("Data"));
                                Log.e("SDATA", "onDataChange: " + sData.toString());
                                listData.add(sData);
                            }
                            Log.e(TAG, "onDataChange: " + listData.get(0).getInfo() );
                            List<String> info = new ArrayList<>();
                            List<String> date = new ArrayList<>();
                            List<String> location = new ArrayList<>();
                            List<String> note = new ArrayList<>();
                            for(int i = 0; i < listData.size(); i++){
                                info.add(listData.get(i).getInfo());
                                date.add(listData.get(i).getDate());
                                location.add(listData.get(i).getLoc());
                                note.add(listData.get(i).getNote());
                            }
                            Log.e("INFO SIZE", "onItemSelected: "+ info);
                            String[] infoArray = new String[info.size()];
                            infoArray = info.toArray(infoArray);
                            String[] dateArray = new String[date.size()];
                            dateArray = date.toArray(dateArray);
                            String[] locArray = new String[location.size()];
                            locArray = location.toArray(locArray);
                            String [] noteArray = new String[note.size()];
                            noteArray = note.toArray(noteArray);

                            for(int i=0; i<infoArray.length; i++) {
                                final ListModel item = new ListModel(infoArray[i], dateArray[i], locArray[i], noteArray[i]);
                                customList.add(item);
                            }
                            Log.e("Check", "ArraySize: " + customList.size());
                            list.setAdapter(listAdapter);
                        }else{
                            list.setAdapter(null);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError e){

                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView parent){

            }
        });
    }
}
