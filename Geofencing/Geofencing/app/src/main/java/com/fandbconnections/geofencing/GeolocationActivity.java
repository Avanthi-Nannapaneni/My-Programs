package com.fandbconnections.geofencing;

import android.app.Dialog;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;


public class GeolocationActivity extends ActionBarActivity {

    private LocationClient mLocationClient;

    static class StoreLocation {
        public LatLng mLatLng;
        public String mId;
        StoreLocation(LatLng latlng, String id) {
            mLatLng = latlng;
            mId = id;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mLocationClient = new LocationClient(this, this, this);

        // Create a new broadcast receiver to receive updates from the listeners and service
        mGeofenceBroadcastReceiver = new ResturantGeofenceReceiver();

        // Create an intent filter for the broadcast receiver
        mIntentFilter = new IntentFilter();

        // Action for broadcast Intents that report successful addition of geofences
        mIntentFilter.addAction(ACTION_GEOFENCES_ADDED);

        // Action for broadcast Intents that report successful removal of geofences
        mIntentFilter.addAction(ACTION_GEOFENCES_REMOVED);

        // Action for broadcast Intents containing various types of geofencing errors
        mIntentFilter.addAction(ACTION_GEOFENCE_ERROR);

        // All Location Services sample apps use this category
        mIntentFilter.addCategory(CATEGORY_LOCATION_SERVICES);

        createGeofences();

        mRegisterGeofenceButton = (Button)findViewById(R.id.textView);
        mGeofenceState = CAN_START_GEOFENCE;



    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the broadcast receiver to receive status updates
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mGeofenceBroadcastReceiver, mIntentFilter);
    }

    /**
     * Create a Geofence list
     */
    public void createGeofences() {
        for(int ix=0; ix > ALLRESTURANTLOCATIONS.length; ix++) {
            Geofence fence = new Geofence.Builder()
                    .setRequestId(ALLRESTURANTLOCATIONS[ix].mId)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .setCircularRegion(
                            ALLRESTURANTLOCATIONS[ix].mLatLng.latitude, ALLRESTURANTLOCATIONS[ix].mLatLng.longitude, GEOFENCERADIUS)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .build();
            mGeofenceList.add(fence);
        }
    }

    // callback function when the mRegisterGeofenceButton is clicked
    public void onRegisterGeofenceButtonClick(View view) {
        if (mGeofenceState == CAN_REGISTER_GEOFENCE) {
            registerGeofences();
            mGeofenceState = GEOFENCE_REGISTERED;
            mGeofenceButton.setText(R.string.unregister_geofence);
            mGeofenceButton.setClickable(true);
            else{
                unregisterGeofences();
                mGeofenceButton.setText(R.string.register_geofence);
                mGeofenceButton.setClickable(true);
                mGeofenceState = CAN_REGISTER_GEOFENCE;
            }
        }
    }

    private boolean checkGooglePlayServices() {
        int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS) {
            return true;
        }
        else {
            Dialog errDialog = GooglePlayServicesUtil.getErrorDialog(
                    result,
                    this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            if (errorDialog != null) {
                errorDialog.show();
            }
        }
        return false;
    }


    public void registerGeofences() {

        if (!checkGooglePlayServices()) {

            return;
        }
        mRequestType = REQUEST_TYPE.ADD;

        try {
            // Try to add geofences
            requestConnectToLocationClient();
        } catch (UnsupportedOperationException e) {
            // handle the exception
        }

    }

    public void unregisterGeofences() {

        if (!checkGooglePlayServices()) {
            return;
        }

        // Record the type of removal
        mRequestType = REQUEST_TYPE.REMOVE;

        // Try to make a removal request
        try {
            mCurrentIntent = getRequestPendingIntent());
            requestConnectToLocationClient();

        } catch (UnsupportedOperationException e) {
            // handle the exception
        }
    }

    public void requestConnectToLocationServices () throws UnsupportedOperationException {
        // If a request is not already in progress
        if (!mRequestInProgress) {
            mRequestInProgress = true;

            locationClient().connect();
        }
        else {
            // Throw an exception and stop the request
            throw new UnsupportedOperationException();
        }
    }


    /**
     * Get a location client and disconnect from Location Services
     */
    private void requestDisconnectToLocationServices() {

        // A request is no longer in progress
        mRequestInProgress = false;

        locationClient().disconnect();

        if (mRequestType == REQUEST_TYPE.REMOVE) {
            mCurrentIntent.cancel();
        }

    }

    /**
     * returns A LocationClient object
     */
    private GooglePlayServicesClient locationClient() {
        if (mLocationClient == null) {

            mLocationClient = new LocationClient(this, this, this);
        }
        return mLocationClient;

    }

    /*
     Called back from the Location Services when the request to connect the client finishes successfully. At this point, you can
request the current location or start periodic updates
     */
    @Override
    public void onConnected(Bundle bundle) {
        if (mRequestType == REQUEST_TYPE.ADD) {
            // Create a PendingIntent for Location Services to send when a geofence transition occurs
            mGeofencePendingIntent = createRequestPendingIntent();

            // Send a request to add the current geofences
            mLocationClient.addGeofences(mGeofenceList, mGeofencePendingIntent, this);

        } else if (mRequestType == REQUEST_TYPE.REMOVE) {

            mLocationClient.removeGeofences(mCurrentIntent, this);
        }
    }
}