package com.farmerssales.farmerssales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //Bottom Bar
        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setSelectedItemId(R.id.navigation_profile);

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {

                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext() ,MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_my_saves:
                        startActivity(new Intent(getApplicationContext() ,MySavesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_post_ad:
                        startActivity(new Intent(getApplicationContext() ,PostAddActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Navigation_Message:
                        startActivity(new Intent(getApplicationContext() ,MessageActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_profile:
                        return true;
                }

                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        //
    }

    public void toCreateAcount(View view) {
        Intent createAccountIntent = new Intent(ProfileActivity.this,CreateAccountActivity.class);
        startActivity(createAccountIntent);
    }
}