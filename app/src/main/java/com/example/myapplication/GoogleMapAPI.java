package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapAPI extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mapAPI;
    SupportMapFragment mapFragment;

    private double v1;
    private double v2;
    private String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_a_p_i);

        Intent i=getIntent();
        v1=i.getDoubleExtra("v1",43.464216);
        v2=i.getDoubleExtra("v2",-80.54161);
        name=i.getStringExtra("name");

        mapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI=googleMap;


        LatLng Canada=new LatLng(v1,v2);
        mapAPI.addMarker(new MarkerOptions().position(Canada).title(name));

        mapAPI.moveCamera(CameraUpdateFactory.newLatLng(Canada));


    }
}