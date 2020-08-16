package com.farmerssales.farmerssales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginOptionsActivity extends AppCompatActivity {

    SwitchCompat toggle_switch;
    Button otp_sendOTP,otp_signin;
    TextInputLayout textInputLayoutID,textInputLayoutPass;
    LottieAnimationView anim1;

    TextInputEditText tv_id,tv_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        toggle_switch = findViewById(R.id.toggle_switch);
        otp_sendOTP = (Button) findViewById(R.id.otp_sendOTP);
        otp_signin = (Button) findViewById(R.id.otp_signin);
        anim1 = (LottieAnimationView) findViewById(R.id.anim1);

        tv_id = (TextInputEditText) findViewById(R.id.tv_id);
        tv_pass = (TextInputEditText) findViewById(R.id.tv_pass);

        anim1.setVisibility(View.INVISIBLE);

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
                    anim1.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Toast.makeText(LoginOptionsActivity.this, "Mobile OTP", Toast.LENGTH_SHORT).show();
                    otp_sendOTP.setVisibility(View.VISIBLE);
                    textInputLayoutID.setHint("Enter Phone Number");
                    textInputLayoutPass.setHint("Enter OTP");
                    anim1.setVisibility(View.INVISIBLE);
                }
            }
        });
        //SIgnIn Button
        otp_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = tv_id.getText().toString().trim();
                String pass = tv_pass.getText().toString().trim();
                if (toggle_switch.isChecked())
                {
                    Toast.makeText(LoginOptionsActivity.this, "Password signin", Toast.LENGTH_SHORT).show();
                    if (isValidMail(id) && !pass.equals(""))
                    {
                        anim1.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(LoginOptionsActivity.this, "Check Email/password", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(LoginOptionsActivity.this, "otp signin", Toast.LENGTH_SHORT).show();
                    if (isValidMob(id) && !pass.equals(""))
                    {
                        anim1.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        Toast.makeText(LoginOptionsActivity.this, "Check OTP", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private boolean isValidMail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private void sendOTP(String id) {
        //OTP Sending Process - Firebase
    }

    private boolean isValidMob(String s) {
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    public void toBackPress(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //OTP send button
    public void sendOtpNow(View view) {

        String id = tv_id.getText().toString().trim();
        if (isValidMob(id))
        {
            //OTP Sending Process - sendOTP(String id)
            anim1.setVisibility(View.VISIBLE);
        }
        else
        {
            Toast.makeText(this, "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
        }

    }
}