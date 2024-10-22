package com.towhid.googlemap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.towhid.googlemap.model.UserLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private DatabaseReference usersRef;
    private Marker userMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        // Initialize Firebase Database Reference
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        locationCallback =new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    updateLocationInDatabase(location);  // Update location in Firebase
                }
            }
        };


        // Check Firebase user authentication
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // User is not signed in, redirect to login activity
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish(); // Optionally, finish the current activity
        } else {
            // User is signed in, proceed with location updates
            checkLocationPermission();
        }


    }


    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }
    }


    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(60000)  // 60 seconds interval
                .setFastestInterval(30000)  // 30 seconds fastest interval
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);  // High accuracy for location updates

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }


    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }


    private void updateLocationInDatabase(Location location) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String userEmail = currentUser.getEmail(); // Get the user's email

            // Use the user's email as the username, if the email is available
            String username = (userEmail != null) ? userEmail : "User";

            // Create UserLocation object with the user's email as the username
            UserLocation userLocation = new UserLocation(username, location.getLatitude(), location.getLongitude());

            // Update the user's location in Firebase
            usersRef.child(userId).setValue(userLocation).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("MapActivity", "Location updated successfully");
                } else {
                    Log.e("MapActivity", "Failed to update location: " + task.getException());
                }
            });

            // Update the map with the user's current location
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (userMarker != null) {
                userMarker.setPosition(latLng); // Update existing marker position
            } else {
                userMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("You")); // Add new marker
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        } else {
            Log.e("MapActivity", "Current user is null.");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    startLocationUpdates();
                }
            } else {
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void fetchAllUserLocations() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear existing markers before adding new ones
                mMap.clear();

                // Fetch and display all users' locations
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserLocation userLocation = snapshot.getValue(UserLocation.class);
                    System.out.println(userLocation);
                    System.out.println("********************************");
                    if (userLocation != null) {
                        LatLng latLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(userLocation.getUsername()));
                    }
                }

                // Re-add the current user's marker if available
                if (userMarker != null) {
                    userMarker = mMap.addMarker(new MarkerOptions().position(userMarker.getPosition()).title("You"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MapActivity", "Failed to fetch user locations", databaseError.toException());
            }
        });
    }







    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            startLocationUpdates();
            fetchAllUserLocations(); // Fetch all user locations
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }


    }


}