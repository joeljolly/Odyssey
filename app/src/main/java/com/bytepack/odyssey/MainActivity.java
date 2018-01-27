package com.bytepack.odyssey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*// Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());*/

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            //Toast.makeText(MainActivity.this,"Welcome",Toast.LENGTH_LONG).show();
            return;
        } else {
            //Intent intent = new Intent(MainActivity.this, login.class);
            //startActivity(intent);
            Toast.makeText(MainActivity.this,"Not Welcome",Toast.LENGTH_LONG).show();
            /*mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                //mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }*/
        }

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    /*public native String stringFromJNI();*/
}
