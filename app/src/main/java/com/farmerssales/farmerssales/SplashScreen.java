package com.farmerssales.farmerssales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.farmerssales.farmerssales.UserDetails.UserDetails;

import io.paperdb.Paper;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);

        String UserPhoneKey = Paper.book().read(UserDetails.UserPhoneKey);
        if(UserPhoneKey !="")
        {
            if (!TextUtils.isEmpty(UserPhoneKey))
            {
                allowAccess(UserPhoneKey);

               /* loading.setTitle("Login in user");
                loading.setMessage("...Already Loged in..");
                loading.setCanceledOnTouchOutside(false);
                loading.show();*/
                Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT).show();
            }
            else
            {
                newUser();
            }
        }
        else
        {
            newUser();
        }
    }

    private void newUser() {
        Toast.makeText(this, "New User", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void allowAccess(String userPhoneKey) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}