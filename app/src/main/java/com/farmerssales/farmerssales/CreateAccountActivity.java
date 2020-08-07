package com.farmerssales.farmerssales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void toCreateAccountProcess(View view) {
        Toast.makeText(this, "Account creation process", Toast.LENGTH_SHORT).show();
    }

    public void backprofile(View view) {
        Intent backIntent = new Intent(CreateAccountActivity.this,ProfileActivity.class);
        startActivity(backIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(CreateAccountActivity.this,ProfileActivity.class);
        startActivity(backIntent);
        finish();
    }
}