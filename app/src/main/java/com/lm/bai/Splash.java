package com.lm.bai;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lm.bai.LoginPOJO.LoginBean;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Splash extends AppCompatActivity {

    Timer timer;

    ProgressBar bar;


    String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CAMERA};

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        bar = (ProgressBar) findViewById(R.id.progress);

        if (hasPermissions(this, PERMISSIONS)) {

            startApp();

        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
        }

        bar = findViewById(R.id.progress);


    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                startApp();

            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    Toast.makeText(getApplicationContext(), "Permissions are required for this app", Toast.LENGTH_SHORT).show();
                    finish();

                }
                //permission is denied (and never ask again is  checked)
                //shouldShowRequestPermissionRationale will return false
                else {
                    Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                    finish();
                    //                            //proceed with logic by disabling the related features or quit the app.
                }
            }

        }


    }


    public void startApp() {

            String ph = SharePreferenceUtils.getInstance().getString("phone");
            String p = SharePreferenceUtils.getInstance().getString("password");


            if (ph.length() > 0 && p.length() > 0) {

                bar.setVisibility(View.VISIBLE);

                Bean b = (Bean) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.BaseUrl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiInterface cr = retrofit.create(AllApiInterface.class);

                Call<LoginBean> call = cr.login(ph, p);

                call.enqueue(new Callback<LoginBean>() {
                    @Override
                    public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {

                        if (Objects.equals(response.body().getStatus() , "1")){

                            SharePreferenceUtils.getInstance().saveString("userId" , response.body().getData().getUserId());
                            SharePreferenceUtils.getInstance().saveString("name" , response.body().getData().getFirstName() + " " + response.body().getData().getLastName());

                            Intent i = new Intent(Splash.this , MainActivity.class);
                            startActivity(i);

                            finish();


                        }else {

                            Toast.makeText(Splash.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                        bar.setVisibility(View.GONE);



                    }

                    @Override
                    public void onFailure(Call<LoginBean> call, Throwable t) {
                        bar.setVisibility(View.GONE);


                    }
                });

            } else {

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        Intent i = new Intent(Splash.this, Login.class);
                        startActivity(i);
                        finish();

                    }
                }, 1500);

            }




    }


}
