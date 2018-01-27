package com.bytepack.odyssey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    GridLayout G2,G3,G1;
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
                    G1.setVisibility(View.VISIBLE);
                    G2.setVisibility(View.GONE);
                    G3.setVisibility(View.GONE);
                    G4.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    G1.setVisibility(View.GONE);
                    G2.setVisibility(View.VISIBLE);
                    G3.setVisibility(View.GONE);
                    G4.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_notifications1:
                    G1.setVisibility(View.GONE);
                    G2.setVisibility(View.GONE);
                    G3.setVisibility(View.VISIBLE);
                    G4.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_notifications2:
                    user_profile();
                    G1.setVisibility(View.GONE);
                    G2.setVisibility(View.GONE);
                    G3.setVisibility(View.GONE);
                    G4.setVisibility(View.VISIBLE);
                    user_profile();
                    display_profile();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        G1=(GridLayout) findViewById(R.id.layout1);
        G2=(GridLayout) findViewById(R.id.layout2);
        G3=(GridLayout) findViewById(R.id.layout3);
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
    }

}
