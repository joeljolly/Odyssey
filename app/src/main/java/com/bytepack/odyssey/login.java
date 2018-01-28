package com.bytepack.odyssey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

        // Create and launch sign-in intent
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in'
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(login.this,"Success",Toast.LENGTH_LONG).show();
                String username=user.getUid();
                DatabaseReference myRef = database.getReference("root/users/"+username+"/activeTask");
                myRef.setValue("null");
                startActivity(new Intent(login.this,HomeActivity.class));

                // ...
            } else {
                // Sign in failed, check response for error code
                Toast.makeText(login.this,"Not Success",Toast.LENGTH_LONG).show();
                // ...
            }
        }
    }


}