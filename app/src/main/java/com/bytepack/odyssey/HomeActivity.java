package com.bytepack.odyssey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    private ImageView imgProfilePic;

    private TextView mTextMessage;
    GeoDataClient g1;
    FusedLocationProviderClient mFusedLocationProviderClient;
    PlaceDetectionClient p1;
    LatLng L;
    GridLayout G1,G2,G3;
    RelativeLayout G4;
    private String mUsername;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    //a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("root/user");


    /////////
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    user_tasks();
                    G1.setVisibility(View.VISIBLE);
                    G2.setVisibility(View.GONE);
                    G4.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    G1.setVisibility(View.GONE);
                    G2.setVisibility(View.VISIBLE);
                    G4.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_notifications2:
                    user_profile();
                    G1.setVisibility(View.GONE);
                    G2.setVisibility(View.GONE);
                    G4.setVisibility(View.VISIBLE);
                    display_profile();
                    return true;
            }
            return false;
        }
    };
    public final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        G1=(GridLayout) findViewById(R.id.layout1);
        G2=(GridLayout) findViewById(R.id.layout2);
        G4=(RelativeLayout) findViewById(R.id.layout4);

    }


    public void user_profile()
    {

       // TextView t=(TextView) findViewById(R.id.textView4);
        //ImageView imageView=(ImageView) findViewById(R.id.imageView);
        myRef.setValue("Hello, World!");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mUsername=mFirebaseUser.getDisplayName()+"**"+mFirebaseUser.getUid()+"*"+mFirebaseUser.getEmail()+"**"+mFirebaseUser.getPhotoUrl()+"**"+mFirebaseUser.getProviderId()+"**"+mFirebaseUser.getPhoneNumber();
       //t.setText(mUsername);

    }
    public void display_profile(){

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            TextView mText = (TextView) findViewById(R.id.textviewname);
            mText.setText(personName);
            // String personGivenName = acct.getGivenName();
            // String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            TextView mailText = (TextView) findViewById(R.id.textViewMail);
            mailText.setText(personEmail);
            // String personId = acct.getId();
            // Uri personPhoto = acct.getPhotoUrl();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            imgProfilePic = (ImageView) findViewById(R.id.img_view);
            Glide.with(getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);
        }
        G4=(RelativeLayout) findViewById(R.id.layout4);
        // Construct a GeoDataClient.
        g1 = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        p1 = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        user_tasks();
    }
    public Boolean mLocationPermissionGranted;
    private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }

    }
    Geocoder myLocation;
    List myList;
    public void user_tasks()
    {
        //final TextView t1=(TextView) findViewById(R.id.textView1);
        myLocation = new Geocoder(this,Locale.getDefault());
        //myList = myLocation.getFromLocation(latPoint,lngPoint,1);
        try {
            getLocationPermission();
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Location mLastKnownLocation =(Location) task.getResult();
                            L=new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());

                            try {
                                myList = myLocation.getFromLocation(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude(),1);
                                Address address = (Address) myList.get(0);
                                // sending back first address line and locality
                                //t1.setText(address.getLocality());
                                Intent intent1=new Intent(HomeActivity.this,MapsActivity.class);
                                intent1.putExtra("Slat",mLastKnownLocation.getLatitude());
                                intent1.putExtra("Slng",mLastKnownLocation.getLongitude());
                                intent1.putExtra("Dlat",8.7707);
                                intent1.putExtra("Dlng",76.8836);
                                startActivity(intent1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                          //  Log.d(TAG, "Current location is null. Using defaults.");
                            //Log.e(TAG, "Exception: %s", task.getException());
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }

    }

}
