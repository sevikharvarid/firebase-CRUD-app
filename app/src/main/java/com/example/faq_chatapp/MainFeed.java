package com.example.faq_chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainFeed extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private HomeFragment homeFragment;
    private AskFragment askFragment;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);

        homeFragment = new HomeFragment();
        askFragment = new AskFragment();
        bottomNavigationView = findViewById(R.id.mainNavBar);
        frameLayout = findViewById(R.id.navFrame);

        changeFragment(homeFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navBtnHome:
                        changeFragment(homeFragment);
                        return true;
                    case R.id.navBtnAsk:
                        changeFragment(askFragment);
                        return true;
                    case R.id.navBtnContact:
                        Intent contactUsIntent = new Intent(MainFeed.this, ContactUs.class);
                        startActivity(contactUsIntent);
                        return true;

                    default:
                        return false;
                }
            }
        });

        // active listen to user logged in or not.
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };
        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "OnComplete : " +task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Failed : ", task.getException());
                    Toast.makeText(MainFeed.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }// Add Auth state listener in onStart method.
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }// release listener in onStop
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        changeFragment(homeFragment);
    }

    public void ChangeToHome(){
        changeFragment(homeFragment);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
    }
    public void ChangeToDescribe(){
        Intent i = new Intent(MainFeed.this,DescribeFragment.class);
        startActivity(i);
    }

    private void changeFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.navFrame, fragment);
        fragmentTransaction.commit();

    }


}
