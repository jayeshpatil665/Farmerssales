package com.farmerssales.farmerssales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.farmerssales.farmerssales.UserDetails.UserDetails;
import com.farmerssales.farmerssales.UserDetails.Users;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;

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

        Paper.init(this);
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
                        passAuthinticate(id,pass);
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

    private void passAuthinticate(final String id, final String pass) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[2];
                field[0] = "email";
                field[1] = "password";
                //Creating array for data
                String[] data = new String[2];
                data[0] = id;
                data[1] = pass;
                PutData putData = new PutData("http://farmers.atwebpages.com/FarmerssalesAPI/UserDetails/passwordAuthinticate.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        //End ProgressBar (Set visibility to GONE)
                        if (result.equals("Authorised"))
                        {
                            getUserData(id);
                            Intent intent = new Intent(LoginOptionsActivity.this,MainActivity.class);
                            anim1.setVisibility(View.INVISIBLE);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            anim1.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginOptionsActivity.this, "Error : "+result, Toast.LENGTH_SHORT).show();
                        }
                        Log.i("PutData", result);
                    }
                }
                //End Write and Read data with URL
            }
        });
    }
    //LoadUserData
    private void getUserData(final String userEmail) {
        final Gson gson = new Gson();

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "email";
                //Creating array for data
                String[] data = new String[1];
                data[0] = userEmail;
                PutData putData = new PutData("http://farmers.atwebpages.com/FarmerssalesAPI/UserDetails/privateLoadDataJSON.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if (!result.equals("Error: Database connection"))
                        {
                            Users uData1 = gson.fromJson(DecodeString(result),Users.class);

                            saveUserDataLocaly(uData1);

                        }
                        else
                        {
                            Toast.makeText(LoginOptionsActivity.this, "Error : in data retrival "+result, Toast.LENGTH_SHORT).show();
                        }
                        Log.i("PutData", result);
                    }
                }
                //End Write and Read data with URL
            }
        });
    }

    public static String DecodeString(String string) {
        String tempStr = string;
        tempStr = tempStr.replace("[", "");
        return tempStr.replace("]", "");
    }
//Save user Data Locally--------------------------------------------------------
private void saveUserDataLocaly(Users uData1) {

    Paper.book().write(UserDetails.UserSkipKey,"NotSkiped");

    Paper.book().write(UserDetails.UserPhoneKey,uData1.getPhone_number());
    Paper.book().write(UserDetails.UserEmailKey,uData1.getEmail());
    Paper.book().write(UserDetails.UserPasswordKey,uData1.getPassword());
    Paper.book().write(UserDetails.UserfNameKey,uData1.getFirst_name());
    Paper.book().write(UserDetails.UserlNameKey,uData1.getLast_name());

    Paper.book().write(UserDetails.UserAddressKey,uData1.getAddress());
    Paper.book().write(UserDetails.UserStateKey,uData1.getState());
    Paper.book().write(UserDetails.UserDistrictKey,uData1.getDistrict());
    Paper.book().write(UserDetails.UserPinKey,uData1.getPincode());

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