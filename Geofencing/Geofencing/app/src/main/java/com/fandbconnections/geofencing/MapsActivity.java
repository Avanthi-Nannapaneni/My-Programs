package com.fandbconnections.geofencing;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private static final LatLng CHANDLER = new LatLng(33.455,-112.0668);
    private static final StoreLocation[] ALLRESTURANTLOCATIONS = new StoreLocation[] {

            new StoreLocation(new LatLng(33.455,-112.0668), new String("Phoenix, AZ")),

            new StoreLocation(new LatLng(33.5123,-111.9336), new String("SCOTTSDALE, AZ")),

            new StoreLocation(new LatLng(33.3333,-111.8335), new String("Chandler, AZ")),

            new StoreLocation(new LatLng(33.4296,-111.9436), new String("Tempe, AZ")),

            new StoreLocation(new LatLng(33.4152,-111.8315), new String("Mesa, AZ")),

            new StoreLocation(new LatLng(33.3525,-111.7896), new String("Gilbert, AZ"))

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CHANDLER, ZOOM_LEVEL));

            Drawable iconDrawable = getResources().getDrawable(R.drawable.ic_launcher);

            Bitmap iconBmp = ((BitmapDrawable) iconDrawable).getBitmap();

            for (int ix = 0; ix < ALLRESTURANTLOCATIONS.length; ix++) {

                mMap.addMarker(new MarkerOptions()

                        .position(ALLRESTURANTLOCATIONS[ix].mLatLng)

                        .icon(BitmapDescriptorFactory.fromBitmap(iconBmp)));

                // Check if we were successful in obtaining the map.
                if (mMap != null) {
                    setUpMap();
                }
            }
        }

        /**
         * This is where we can add markers or lines, add listeners or move the camera. In this case, we
         * just add a marker near Africa.
         * <p/>
         * This should only be called once and when we are sure that {@link #mMap} is not null.
         */

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
