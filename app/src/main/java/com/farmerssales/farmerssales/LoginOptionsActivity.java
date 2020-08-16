package com.farmerssales.farmerssales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginOptionsActivity extends AppCompatActivity {

    SwitchCompat toggle_switch;
    Button otp_sendOTP,otp_signin;
    TextInputLayout textInputLayoutID,textInputLayoutPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        toggle_switch = findViewById(R.id.toggle_switch);
        otp_sendOTP = (Button) findViewById(R.id.otp_sendOTP);
        otp_signin = (Button) findViewById(R.id.otp_signin);

        textInputLayoutID = (TextInputLayout) findViewById(R.id.textInputLayoutID);
        textInputLayoutPass = (TextInputLayout) findViewById(R.id.textInputLayoutPass);


        otp_sendOTP.setVisibility(View.VISIBLE);

        toggle_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle_switch.isChecked())
                {
                    Toast.makeText(LoginOptionsActivity.this, "Password Signin", Toast.LENGTH_SHORT).show();
                    otp_sendOTP.setVisibility(View.INVISIBLE);
                    textInputLayoutID.setHint("Enter Email ID");
                    textInputLayoutPass.setHint("Enter Password");
                }
                else
                {
                    Toast.makeText(LoginOptionsActivity.this, "Mobile OTP", Toast.LENGTH_SHORT).show();
                    otp_sendOTP.setVisibility(View.VISIBLE);
                    textInputLayoutID.setHint("Enter Phone Number");
                    textInputLayoutPass.setHint("Enter OTP");
                }
            }
        });
    }

    public void toBackPress(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}