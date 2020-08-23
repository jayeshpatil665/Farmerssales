package com.farmerssales.farmerssales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.farmerssales.farmerssales.UserDetails.UserDetails;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Paper.init(this);
    }

    @Override
    public void onBackPressed() {
        //
    }

    public void toCreateNewAcount(View view) {
        Intent createAccountIntent = new Intent(HomeActivity.this,CreateAccountActivity.class);
        startActivity(createAccountIntent);
    }

    public void toSkipStep(View view) {
        Paper.book().write(UserDetails.UserSkipKey,"skiped");

        Intent skipIntent = new Intent(HomeActivity.this,MainActivity.class);
        startActivity(skipIntent);
        finish();
    }

    public void toLoginOptions(View view) {
        Intent LoginAccountIntent = new Intent(HomeActivity.this,LoginOptionsActivity.class);
        startActivity(LoginAccountIntent);
    }
}