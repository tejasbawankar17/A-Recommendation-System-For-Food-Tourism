package com.parsh.rrs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private HarryAdapter adapter;
    private LocationManager locationManager;

    private String[] arr = {
            "Fast food restaurant", "Family restaurant", "South Indian restaurant", "Food court",
            "Marathi restaurant", "Barbecue restaurant", "Bakery", "North Indian restaurant",
            "Indian restaurant", "Bar & grill", "Mughlai restaurant", "Non vegetarian restaurant",
            "Pizza restaurant", "Vegetarian restaurant", "Rajasthani restaurant",
            "Breakfast restaurant", "Italian restaurant", "Punjabi restaurant", "Candy store",
            "Chicken restaurant", "Fine dining restaurant", "Biryani restaurant", "Sandwich shop",
            "Middle Eastern restaurant", "Takeout restaurant", "Small plates restaurant",
            "American restaurant", "Chinese restaurant", "Dhaba", "Juice shop", "Grill",
            "Shawarma restaurant", "Bar", "Momo restaurant", "Asian restaurant", "Buffet restaurant",
            "Bengali restaurant", "Country food restaurant", "Hyderabadi restaurant",
            "Seafood restaurant", "Dessert shop", "Health food restaurant", "Vegan restaurant",
            "Ice cream shop", "Hamburger restaurant", "Diner", "Seafood donburi restaurant",
            "Gujarati restaurant", "Snack bar", "Indian takeaway", "Fish & chips restaurant",
            "Hoagie restaurant", "Cold noodle restaurant", "Cape Verdean restaurant",
            "Afghan restaurant", "Tiffin center", "Continental restaurant", "Mandarin restaurant",
            "Chinese noodle restaurant", "Fondue restaurant", "Sundae restaurant", "Fish restaurant",
            "Rice restaurant", "Korean restaurant"
    };

    public Integer[] images = {
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage, R.drawable.trailimage,
            R.drawable.trailimage
    };

    private double latitude;
    private double longitude;

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 56);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                latitude = lastKnownLocation.getLatitude();
                longitude = lastKnownLocation.getLongitude();
            }
            adapter = new HarryAdapter(MainActivity.this, R.layout.my_harry_layout, arr, images, latitude, longitude);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 56 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 56);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }
}
