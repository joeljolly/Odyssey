package com.bytepack.odyssey;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;


import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private Boolean mLocationPermissionGranted = false;
    public GoogleMap mMap;
    private TextView mTapTextView;
    private Location mLocation,Dest;
    private  com.bytepack.odyssey.GPSTracker gpsTracker;
    private static final String TAG = MapsActivity.class.getSimpleName();
    double latitude, longitude;
    double lat, lng;
    ImageView imgProfilePic;
    GoogleSignInAccount acct;
    String personPhotoUrl;
    Bitmap bm;
    LatLng spos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        try {
            acct = GoogleSignIn.getLastSignedInAccount(this);
            personPhotoUrl = acct.getPhotoUrl().toString();
            gpsTracker = new com.bytepack.odyssey.GPSTracker(getApplicationContext());
            mLocation = gpsTracker.getLocation();

            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
          /*  Geocoder geocoder = new Geocoder(this);
            geocoder.get*/
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment
                    mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            System.out.println("60");
            lat = gpsTracker.getLocation().getLatitude();
            lng = gpsTracker.getLocation().getLongitude();



        } catch (Exception e) {
            Toast.makeText(MapsActivity.this,"GPS not enabled!",Toast.LENGTH_LONG);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    public int getZoomLevel(Circle circle) {
        int zoomlevel = 11;
        if (circle != null) {
            double radius = circle.getRadius() + circle.getRadius() / 2;
            double scale = radius / 500;
            zoomlevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomlevel;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        System.out.println("118");
        Bundle b= getIntent().getExtras();
        double Dlat;
        double Dlng;
        String aDlat =b.getString("lat");
        String aDlng =b.getString("lng");
        String place =b.getString("name");
        Dlat=Double.parseDouble(aDlat);
        Dlng=Double.parseDouble(aDlng);
        Dest = new Location("Dest");
        Dest.setLatitude(Dlat);
        Dest.setLongitude(Dlng);
        Toast.makeText(MapsActivity.this,""+aDlat+" "+aDlng+"",Toast.LENGTH_LONG).show();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocationName(place, 1);
          //  Dlat = addresses.get(0).getLatitude();
           // Dlng = addresses.get(0).getLongitude();
        }
        catch (Exception e)
        {}
        //CircleOptions circleoptions = new CircleOptions().strokeWidth(2).strokeColor(Color.BLUE).fillColor(Color.parseColor("#500084d3"));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(Slat,Slng)).title("Sthalam1"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Slat,Slng)));
        //Circle circle = mMap.addCircle(circleoptions.center(new LatLng(Slat,Slng)).radius(5000.0));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circleoptions.getCenter(), getZoomLevel(circle)));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Dlat,Dlng))
                        .title(place)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag)));
    }
    public String getAddress(LatLng point)
    {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            return address;
        }
        catch(Exception e)
        {
            Toast.makeText(MapsActivity.this,"GPS not enabled!",Toast.LENGTH_LONG);
        }
        return "Invalid Location";
    }
    @Override
    public void onMapClick(LatLng point) {
     mMap.clear();
        System.out.println("125");
        CircleOptions circleoptions = new CircleOptions().strokeWidth(2).strokeColor(Color.BLUE).fillColor(Color.parseColor("#500084d3"));
        try {
           // String st = acct.getPhotoUrl().toString();
            //URL url=new URL(st);
           // Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
          //  MarkerOptions mo= new MarkerOptions()
           //         .icon(BitmapDescriptorFactory.fromBitmap(bmp));
          //  mMap.addMarker(mo.position(new LatLng(8,76)).title("HII"));

            com.bytepack.odyssey.GPSTracker gpsTracker = new com.bytepack.odyssey.GPSTracker(getApplicationContext());
            mLocation = gpsTracker.getLocation();

            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
          /*  Geocoder geocoder = new Geocoder(this);
            geocoder.get*/
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment
                    mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            lat = gpsTracker.getLocation().getLatitude();
            lng = gpsTracker.getLocation().getLongitude();
            LatLng currloc = new LatLng(lat, lng);
             mMap.addMarker(new MarkerOptions()
                    .position(currloc)
                    .title(getAddress(currloc))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.user)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currloc));
            Circle circle = mMap.addCircle(circleoptions.center(currloc).radius(50));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(circleoptions.getCenter(), getZoomLevel(circle)));
            if(mLocation.distanceTo(Dest)<=50)
            {
                Toast.makeText(MapsActivity.this,"Success",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(MapsActivity.this,"Fail",Toast.LENGTH_LONG).show();
            }
           //final TextView textViewToChange = (TextView) findViewById(R.id.cord);
            //textViewToChange.setText("Latittude:"+point.latitude+"\nLongitude"+point.longitude);
            System.out.println("149");
        } catch (Exception e) {
            //toast

        }
        System.out.println("153");
    }

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
}