package tb.beacon;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;
import java.util.List;
import java.util.jar.Manifest;

public class BeaconMap extends FragmentActivity {

    protected GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private final String TAG = "BeaconMap";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_map);
        setUpMapIfNeeded();
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
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
            Log.d(TAG, "mMap created");
            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        ParseQuery query = ParseQuery.getQuery(VARS.P_OBJ_NAME);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.d(TAG, "Finished finding in background");
                if (e == null) {
                    if (objects.size() > 0){
                        Log.d(TAG,"This is the size of the object " + objects.size());
                    }
                    for (ParseObject objs : objects) {
                        Date currentDate = new Date();
                        long timeDiff = currentDate.getTime() - ((Date) objs.getCreatedAt()).getTime();
                        timeDiff /= (1000 * 60);
                        Log.d(TAG, "This is the time difference" + timeDiff);
                        if(timeDiff < Integer.parseInt((String) objs.get(VARS.DB_B_DURATION)))
                            mMap.addMarker(new MarkerOptions().position(new LatLng((double) objs.get(VARS.DB_B_LAT), (double) objs.get(VARS.DB_B_LONG))).title((String) objs.get(VARS.DB_B_NAME)));
                    }
                    Log.d(TAG, "No error");
                }
                else {
                    Log.d(TAG, "ERROR!!!");
                    Log.e(TAG, e.getStackTrace().toString());
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }
}
