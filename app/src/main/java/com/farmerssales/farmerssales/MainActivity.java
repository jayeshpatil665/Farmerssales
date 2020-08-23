package com.farmerssales.farmerssales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.farmerssales.farmerssales.UserDetails.UserDetails;
import com.farmerssales.farmerssales.UserDetails.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    TabLayout top_tab;

    Button btn1;

    TextView top_u_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top_u_name = findViewById(R.id.top_u_name);

        //FragmentManagerReference
        final FragmentManager manager = this.getSupportFragmentManager();
        manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.fragment2))
                .show(manager.findFragmentById(R.id.fragment1))
                .commit();


        //Categories Buttons References
        btn1 = findViewById(R.id.btn1);


        //TOP Tab
        top_tab = (TabLayout) findViewById(R.id.tab);

        Paper.init(this);
        final String UserPhoneKey = Paper.book().read(UserDetails.UserfNameKey);
        if (UserPhoneKey !="") {
            top_u_name.setText(UserPhoneKey);
        }
        String UserSkipKey = Paper.book().read(UserDetails.UserSkipKey);
        if (UserSkipKey.equals("skiped")) {
            top_u_name.setText("Hello User");
        }

        top_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition())
                {
                    case 0:
                        manager.beginTransaction()
                                .hide(manager.findFragmentById(R.id.fragment2))
                                .show(manager.findFragmentById(R.id.fragment1))
                                .commit();
                        break;
                    case 1:
                        manager.beginTransaction()
                                .hide(manager.findFragmentById(R.id.fragment1))
                                .show(manager.findFragmentById(R.id.fragment2))
                                .commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Bottom Bar
        BottomNavigationView bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setSelectedItemId(R.id.navigation_home);

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.navigation_home:
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


        //Categories onClick Listeners
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Grains Button", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        //
    }
}