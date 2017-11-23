// App: Sensor Collector
// Author: Bradley Chippi
// Course: CS390
// Instructor: Dr. Corser

package com.example.bradleychippi.water_test_kit;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * This class utilizes the location services in Android to grab the last known latitude
 * and longitude.
 *
 * @author Bradley Chippi
 * @version 1.0 Nov 23, 2017
 */

public class Loc implements LocationListener {

    private LocationManager locationManager;
    private String latitude = "0.0";
    private String longitude = "0.0";
    private Criteria criteria;
    private String provider;

    /**
     * Location constructor. Initiates location manager with fine accuracy, then requests
     * location updates.
     *
     * @param context   Current state of app
     */
    public Loc(Context context) {
        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = locationManager.getBestProvider(criteria, true);
        try{
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1,
                    0, this);
            setMostRecentLocation(locationManager.getLastKnownLocation(provider));
        }catch(SecurityException e){
            Log.d("FORM",e.getMessage());
        }



    }

    /**
     * Required
     *
     * @param lastKnownLocation
     */
    private void setMostRecentLocation(Location lastKnownLocation) {

    }

    /**
     * Gets last known latitude
     *
     * @return latitute
     */
    public Double getLatitude() {
        try {
            return locationManager.getLastKnownLocation(provider).getLatitude();
        }catch(SecurityException e){
            Log.d("FORM",e.getMessage());
        }
        return 0.0;
    }

    /**
     *Gets last known longitude
     *
     * @return longitude
     */
    public Double getLongitude() {
        try {
            return locationManager.getLastKnownLocation(provider).getLongitude();
        }catch(SecurityException e){
            Log.d("FORM",e.getMessage());
        }
        return 0.0;
    }

    /**
     * Required
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        double lon = (double) (location.getLongitude());/// * 1E6);
        double lat = (double) (location.getLatitude());// * 1E6);
        Log.d("LOC", Double.toString(location.getLatitude()));
        latitude = lat + "";
        longitude = lon + "";
    }

    /**
     * Required
     *
     * @param arg0
     */
    @Override
    public void onProviderDisabled(String arg0) {

    }

    /**
     * Required
     *
     * @param arg0
     */
    @Override
    public void onProviderEnabled(String arg0) {

    }

    /**
     * Required
     *
     * @param arg0
     * @param arg1
     * @param arg2
     */
    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

    }

}