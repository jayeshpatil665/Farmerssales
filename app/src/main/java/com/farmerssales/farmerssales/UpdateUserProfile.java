package com.farmerssales.farmerssales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.farmerssales.farmerssales.UserDetails.UserDetails;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import io.paperdb.Paper;

import static com.farmerssales.farmerssales.CreateAccountActivity.isValid;
import static com.farmerssales.farmerssales.CreateAccountActivity.isValidMob;
import static com.farmerssales.farmerssales.UserDetails.UserDetails.UserIDKey;

public class UpdateUserProfile extends AppCompatActivity {

    EditText u_fName,u_lName,u_pNumber,u_eMail,u_address,u_state,u_district,u_pin,u_pass;

    String outToast="",validFor="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);

        u_fName = findViewById(R.id.ed1);
        u_lName = findViewById(R.id.ed2);
        u_pNumber = findViewById(R.id.ed3);
        u_eMail = findViewById(R.id.ed4);
        u_address = findViewById(R.id.ed5);
        u_state = findViewById(R.id.ed6);
        u_district = findViewById(R.id.ed7);
        u_pin = findViewById(R.id.ed8);
        u_pass = findViewById(R.id.ed9);

        Paper.init(this);
        String UseridKey = String.valueOf(Paper.book().read(UserIDKey));
        if(UseridKey !="")
        {
            LoadPreviousDataFromDB(UseridKey);
        }
        else {//  newUser();
        }
    }

    private void LoadPreviousDataFromDB(String userPhoneKey) {
       // u_pass.setText(Paper.book().read(UserDetails.UserPasswordKey).toString());
       // String.valueOf()
        u_fName.setText(String.valueOf(Paper.book().read(UserDetails.UserfNameKey)));
        u_lName.setText(String.valueOf(Paper.book().read(UserDetails.UserlNameKey)));
        Log.i("phone",UserDetails.UserPhoneKey);
        u_pNumber.setText(String.valueOf(Paper.book().read(UserDetails.UserPhoneKey)));
        u_eMail.setText(String.valueOf(Paper.book().read(UserDetails.UserEmailKey)));

        u_address.setText(String.valueOf(Paper.book().read(UserDetails.UserAddressKey)));
        u_state.setText(String.valueOf(Paper.book().read(UserDetails.UserStateKey)));
        u_district.setText(String.valueOf(Paper.book().read(UserDetails.UserDistrictKey)));
        u_pin.setText(String.valueOf(Paper.book().read(UserDetails.UserPinKey)));
    }


    public void toDatabase(View view) {
        String ufname,ulName,upNumber,ueMail,uaddress,ustate,udistrict,upin,upass;


        ufname = u_fName.getText().toString().trim();
        ulName = u_lName.getText().toString().trim();
        upNumber = u_pNumber.getText().toString().trim();
        ueMail = u_eMail.getText().toString().trim();
        uaddress = u_address.getText().toString().trim();
        ustate = u_state.getText().toString().trim();
        udistrict = u_district.getText().toString().trim();
        upin = u_pin.getText().toString().trim();
        upass = u_pass.getText().toString().trim();

        if (validateFields(ufname,ulName,upNumber,ueMail,uaddress,ustate,udistrict,upin,upass)){
            if (!validFor.equals("")){
                if (validFor.equals("EmailMob")){
                    updateDetails(ufname,ulName,upNumber,ueMail,uaddress,ustate,udistrict,upin,upass);
                }
                else if (validFor.equals("Mob")){
                    String emptyEmail = " ";
                    updateDetails(ufname,ulName,upNumber,emptyEmail,uaddress,ustate,udistrict,upin,upass);
                }
            }
        }
        else{
            Toast.makeText(this,""+outToast,Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDetails(final String ufname, final String ulName, final String upNumber, final String ueMail, final String uaddress, final String ustate, final String udistrict, final String upin, final String upass) {


        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[12];
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
                field[11] = "id";
                //Creating array for data
                String[] data = new String[12];
                data[0] = ufname;
                data[1] = ulName;
                data[2] = upNumber;
                data[3] = ueMail;
                data[4] = upass;

                data[5] = uaddress;
                data[6] = udistrict;
                data[7] = ustate;
                data[8] = upin;

                data[9] = "0";
                data[10] = "0";
                data[11] = String.valueOf(Paper.book().read(UserIDKey));
                PutData putData = new PutData("http://farmers.atwebpages.com/FarmerssalesAPI/UserDetails/updateUserData.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        //End ProgressBar (Set visibility to GONE)
                        if (result.equals("Success"))
                        {
                            Toast.makeText(UpdateUserProfile.this, "Updation Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateUserProfile.this,SplashScreen.class));
                        }
                        else
                        {
                            Toast.makeText(UpdateUserProfile.this, "Updation Failed", Toast.LENGTH_SHORT).show();
                        }
                        Log.i("PutData", result);
                    }
                }
                //End Write and Read data with URL
            }
        });
    }

    private boolean validateFields(String ufname, String ulName, String upNumber, String ueMail, String uaddress, String ustate, String udistrict, String upin, String upass) {

        if (!ueMail.equals("")){
            if (ufname.equals("") || ulName.equals("") || upNumber.equals("") || uaddress.equals("") || ustate.equals("") || udistrict.equals("") || upin.equals("") || upass.equals("")){
                outToast = "All fields are required";
                return false;
            }
            else{
                if (isValid(ueMail) && isValidMob(upNumber)){
                    validFor="EmailMob";
                    return  true;
                }else {
                    outToast = "Enter Valid Email ID or Phone number";
                    return false;
                }
            }
        }
        else if (ueMail.equals("")){
            if (ufname.equals("") || ulName.equals("") || upNumber.equals("") || uaddress.equals("") || ustate.equals("") || udistrict.equals("") || upin.equals("") || upass.equals("")){
                outToast = "except email ! All fields are required";
                return false;
            }
            else{
                if (isValidMob(upNumber)){
                    validFor="Mob";
                    return  true;
                }else {
                    outToast = "Enter Valid Phone number";
                    return false;
                }
            }
        }
        return false;
    }

    public void cancleOperation(View view) {
        onBackPressed();
    }
}