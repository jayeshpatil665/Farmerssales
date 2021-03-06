package com.farmerssales.farmerssales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.farmerssales.farmerssales.UserDetails.UserDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.paperdb.Paper;

public class PostAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_add);

        //Bottom Bar
        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setSelectedItemId(R.id.navigation_post_ad);

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
                        return true;
                    case R.id.Navigation_Message:
                        startActivity(new Intent(getApplicationContext() ,MessageActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.navigation_profile:
                        String UserSkipKey = Paper.book().read(UserDetails.UserSkipKey);
                        if (UserSkipKey.equals("NotSkiped")) {
                            startActivity(new Intent(getApplicationContext() ,ProfileLogedInActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        }
                        else
                        {
                            startActivity(new Intent(getApplicationContext() ,ProfileActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        }
                }

                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        //
    }
}