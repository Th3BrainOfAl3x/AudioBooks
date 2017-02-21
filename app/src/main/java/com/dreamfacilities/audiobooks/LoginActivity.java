package com.dreamfacilities.audiobooks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

/**
 * Created by alex on 20/02/17.
 */

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;
    private RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = ((App) getApplicationContext()).getAuth();

        container = (RelativeLayout) findViewById(R.id.loginContainer);
        doLogin();
    }

    private void doLogin() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            saveUser(currentUser);
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            String provider = currentUser.getProviders().get(0);
            SharedPreferences pref = getSharedPreferences(
                    "com.dreamfacilities.audiobooks_internal", MODE_PRIVATE);
            pref.edit().putString("provider", provider).commit();
            pref.edit().putString("name", name).commit();
            if (email != null) {
                pref.edit().putString("email", email).commit();
            }
            if(currentUser.isEmailVerified()){
                Intent i = new Intent(this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } else {
                currentUser.sendEmailVerification();
                showSnackbar("You need to verificate your email from your inbox");
            }
        } else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder().setProviders(Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                    .setIsSmartLockEnabled(true).build(), RC_SIGN_IN);
        }
    }
    void saveUser(final FirebaseUser user) {
        DatabaseReference usersReference = ((App) getApplicationContext()).getUsersReference();
        final DatabaseReference currentUserReference = usersReference.child(
                user.getUid());
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    currentUserReference.setValue(new User(user.getDisplayName(), user.getEmail()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        currentUserReference.addListenerForSingleValueEvent(userListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == ResultCodes.OK) {
                doLogin();
                finish();
            }
        }
    }
    private void showSnackbar(String message) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
    }
}
