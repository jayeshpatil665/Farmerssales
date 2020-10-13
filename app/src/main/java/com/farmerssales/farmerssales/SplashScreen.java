package com.farmerssales.farmerssales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.farmerssales.farmerssales.UserDetails.UserDetails;
import com.farmerssales.farmerssales.UserDetails.Users;
import com.google.gson.Gson;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import io.paperdb.Paper;

import static com.farmerssales.farmerssales.UserDetails.UserDetails.UserIDKey;
import static com.farmerssales.farmerssales.UserDetails.UserDetails.UserPhoneKey;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);

        if (isNetworkConnected())
        {
            String UsePhoneKey = String.valueOf(Paper.book().read(UserDetails.UserPhoneKey));
            if(! UsePhoneKey.equals(""))
            {
                Log.i("PhoneNO :",UsePhoneKey);
                this.allowAccess(UsePhoneKey);
            }
            else
            {
                newUser();
            }
        }
        else
        {
            Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show();
            Intent networkError = new Intent(SplashScreen.this,NetworkErrorActivity.class);
            startActivity(networkError);
        }

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void newUser() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void allowAccess(String usePhoneKey) {
       this.checkIfUserExist(usePhoneKey);

       Intent intent = new Intent(this,MainActivity.class);
       startActivity(intent);
       finish();

    }

    private void checkIfUserExist(final String userPhoneKey) {

        final Gson gson = new Gson();

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "id";
                //Creating array for data
                String[] data = new String[1];
                data[0] = String.valueOf(Paper.book().read(UserDetails.UserIDKey));
                PutData putData = new PutData("http://farmers.atwebpages.com/FarmerssalesAPI/UserDetails/checkUser.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();

                        //End ProgressBar (Set visibility to GONE)
                        if (!result.equals("Error: Database connection"))
                        {
                           //User Exist
                            Log.i("PutData", result);
                            Users uData1 = gson.fromJson(DecodeString(result),Users.class);

                            if (uData1 == null)
                            {
                                Paper.book().write(UserDetails.UserExistKey,"notExist");
                                newUser();
                            }
                            else
                            {
                                Paper.book().write(UserDetails.UserExistKey,"Exist");
                                Paper.book().write(UserDetails.UserSkipKey,"NotSkiped");

                                Log.i("phone",uData1.getPhone_number());
                                Paper.book().write(UserDetails.UserPhoneKey,uData1.getPhone_number());
                                Paper.book().write(UserDetails.UserEmailKey,uData1.getEmail());
                                Paper.book().write(UserDetails.UserPasswordKey,uData1.getPassword());
                                Paper.book().write(UserDetails.UserfNameKey,uData1.getFirst_name());
                                Paper.book().write(UserDetails.UserlNameKey,uData1.getLast_name());

                                Paper.book().write(UserDetails.UserAddressKey,uData1.getAddress());
                                Paper.book().write(UserDetails.UserStateKey,uData1.getState());
                                Paper.book().write(UserDetails.UserDistrictKey,uData1.getDistrict());
                                Paper.book().write(UserDetails.UserPinKey,uData1.getPincode());

                                Paper.book().write(UserDetails.UserIDKey,uData1.getId());
                            }

                        }
                        else
                        {
                            // Error
                            Paper.book().write(UserDetails.UserExistKey,"notExist");
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

}