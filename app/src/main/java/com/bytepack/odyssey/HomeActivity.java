package com.bytepack.odyssey;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    GridLayout G1,G2,G3,G4;
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
                    G1.setVisibility(View.GONE);
                    G2.setVisibility(View.GONE);
                    G3.setVisibility(View.GONE);
                    G4.setVisibility(View.VISIBLE);
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
        G4=(GridLayout) findViewById(R.id.layout4);
    }

}
