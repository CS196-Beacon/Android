package com.bc.beacon;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback 
{

	static final LatLng RandomPoint = new LatLng(21, 57);
	private GoogleMap googleMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}
	@Override
	public void onMapReady(GoogleMap map) {
		LatLng sydney = new LatLng(-34, 151);
		map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
		map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
	}
}
