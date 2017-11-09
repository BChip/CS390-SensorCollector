package com.example.bradleychippi.water_test_kit;

/**
 * Created by bradleychippi on 11/08/17.
 */

import android.app.Service;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class Loc implements LocationListener {

    private LocationManager locationManager;
    private String latitude = "0.0";
    private String longitude = "0.0";
    private Criteria criteria;
    private String provider;

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

    private void setMostRecentLocation(Location lastKnownLocation) {

    }

    public Double getLatitude() {
        try {
            return locationManager.getLastKnownLocation(provider).getLatitude();
        }catch(SecurityException e){
            Log.d("FORM",e.getMessage());
        }
        return 0.0;
    }

    public Double getLongitude() {
        try {
            return locationManager.getLastKnownLocation(provider).getLongitude();
        }catch(SecurityException e){
            Log.d("FORM",e.getMessage());
        }
        return 0.0;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.location.LocationListener#onLocationChanged(android.location.
     * Location)
     */
    @Override
    public void onLocationChanged(Location location) {
        double lon = (double) (location.getLongitude());/// * 1E6);
        double lat = (double) (location.getLatitude());// * 1E6);
        Log.d("LOC", Double.toString(location.getLatitude()));
//      int lontitue = (int) lon;
//      int latitute = (int) lat;
        latitude = lat + "";
        longitude = lon + "";

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.location.LocationListener#onProviderDisabled(java.lang.String)
     */
    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.location.LocationListener#onProviderEnabled(java.lang.String)
     */
    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see android.location.LocationListener#onStatusChanged(java.lang.String,
     * int, android.os.Bundle)
     */
    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub

    }

}