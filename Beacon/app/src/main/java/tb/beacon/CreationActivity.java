package tb.beacon;

import android.app.Activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.parse.ParseObject;

import java.util.Date;


public class CreationActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    protected static final String TAG= "CreationActivity";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;

    private TextView locationText;
    private EditText beaconName, minutes;
    private Button createBeaconButton, mapButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);

        locationText = (TextView)findViewById(R.id.Long_Lat);
        beaconName = (EditText)findViewById(R.id.BeaconName);
        minutes = (EditText)findViewById(R.id.num_minutes);

        createBeaconButton = (Button)findViewById(R.id.beacon_creator);
        createBeaconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valuesAreValid()) {
                    Date date = new Date();
                    ParseObject beaconObject = new ParseObject(VARS.P_OBJ_NAME);
                    beaconObject.put(VARS.DB_B_LAT, mLastLocation.getLatitude());
                    beaconObject.put(VARS.DB_B_LONG, mLastLocation.getLongitude());
                    beaconObject.put(VARS.DB_B_NAME, beaconName.getText().toString());
                    beaconObject.saveInBackground();
                    Intent intent = new Intent(v.getContext(), BeaconMap.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(v.getContext(),"Please enter valid values", Toast.LENGTH_SHORT).show();
            }
        });
        mapButton = (Button)findViewById(R.id.maps_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BeaconMap.class);
                startActivity(intent);
            }
        });
        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop(){
        super.onStop();
        if(mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection Failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation!=null){
            locationText.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()) + " Longitude: " + String.valueOf(mLastLocation.getLongitude()));
            Log.d(TAG, "SUCCEEDED IN FINDING LOCATION");
        }
        else
            Log.d(TAG, "FAILED TO FIND LOCATION");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    public boolean valuesAreValid(){
        if(minutes.getText() == null || beaconName.getText() == null)
            return false;
        if(beaconName.getText().toString().length() == 0 || ((int)Integer.parseInt(minutes.getText().toString())<0||(int)Integer.parseInt(minutes.getText().toString())>300) ) {
            Log.d(TAG, "Ended because values are invalid." + " " + minutes.getText().toString() + " " + beaconName.getText().toString());
            return false;
        }
        return true;
    }

}