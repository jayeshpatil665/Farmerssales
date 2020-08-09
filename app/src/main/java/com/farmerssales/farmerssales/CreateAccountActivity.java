package com.farmerssales.farmerssales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateAccountActivity extends AppCompatActivity {

    EditText et_reg_firstname,et_reg_lastname,et_reg_email,et_reg_phone,et_reg_pass;

    ProgressBar progress;
    Button create_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        progress = findViewById(R.id.progress);
        create_btn = findViewById(R.id.create_btn);

        et_reg_firstname = findViewById(R.id.et_reg_firstname);
        et_reg_lastname = findViewById(R.id.et_reg_lastname);
        et_reg_pass = findViewById(R.id.et_reg_pass);
        et_reg_phone = findViewById(R.id.et_reg_phone);
        et_reg_email = findViewById(R.id.et_reg_email);

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName_reg = et_reg_firstname.getText().toString().trim();
                String lName_reg = et_reg_lastname.getText().toString().trim();
                String pass_reg = et_reg_pass.getText().toString().trim();
                String phone_reg = et_reg_phone.getText().toString().trim();

                String email_reg = et_reg_email.getText().toString().trim();


                if (email_reg.equals(""))
                {
                    email_reg = " ";
                }
                if (!fName_reg.equals("") && !lName_reg.equals("") && !pass_reg.equals("") && !phone_reg.equals("") && !email_reg.equals(" "))
                {
                    if (isValidMob(phone_reg) && isValid(email_reg))
                    {
                        progress.setVisibility(View.VISIBLE);

                        insertIntoDB(fName_reg,lName_reg,pass_reg,phone_reg,email_reg);
                    }
                    else
                    {
                        Toast.makeText(CreateAccountActivity.this, " Email / phone not valid", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (!fName_reg.equals("") && !lName_reg.equals("") && !pass_reg.equals("") && !phone_reg.equals("") && email_reg.equals(" "))
                {
                    if (isValidMob(phone_reg))
                    {
                        progress.setVisibility(View.VISIBLE);

                        insertIntoDB(fName_reg,lName_reg,pass_reg,phone_reg,email_reg);
                    }
                    else
                    {
                        Toast.makeText(CreateAccountActivity.this, "phone not valid", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(CreateAccountActivity.this, "Except email All fields are require ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidMob(String s)
    {
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    private void insertIntoDB(final String fName_reg, final String lName_reg, final String pass_reg, final String phone_reg, final String email_reg) {

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[11];
                field[0] = "first_name";
                field[1] = "last_name";
                field[2] = "phone_number";
                field[3] = "email";
                field[4] = "password";

                field[5] = "address";
                field[6] = "district";
                field[7] = "state";
                field[8] = "pincode";

                field[9] = "account_activate";
                field[10] = "login_status";
                //Creating array for data
                String[] data = new String[11];
                data[0] = fName_reg;
                data[1] = lName_reg;
                data[2] = phone_reg;
                data[3] = email_reg;
                data[4] = pass_reg;

                data[5] = "Null";
                data[6] = "Null";
                data[7] = "Null";
                data[8] = "Null";

                data[9] = "0";
                data[10] = "0";

                PutData putData = new PutData("http://farmers.atwebpages.com/FarmerssalesAPI/UserDetails/signup.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        //End ProgressBar (Set visibility to GONE)
                        if (result.equals("Sign Up Success"))
                        {
                            progress.setVisibility(View.INVISIBLE);
                            Toast.makeText(CreateAccountActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateAccountActivity.this,ProfileActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            progress.setVisibility(View.INVISIBLE);
                            Toast.makeText(CreateAccountActivity.this, ""+result, Toast.LENGTH_SHORT).show();
                        }
                        Log.i("PutData", result);
                    }
                }
                //End Write and Read data with URL
            }
        });

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