package com.example.kaamwali;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaamwali.ProfilePOJO.ProfileBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Profile extends AppCompatActivity {

    Toolbar toolbar;

    TextView name, email, pincode, gender, mobile, state, country, city;

    Button modify;

    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.arrow);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        toolbar.setTitle("My Profile");

        name = findViewById(R.id.name);

        gender = findViewById(R.id.male);

        mobile = findViewById(R.id.mobile);

        email = findViewById(R.id.email);

        city = findViewById(R.id.city);

        pincode = findViewById(R.id.pin);

        modify = findViewById(R.id.profile);

        bar = findViewById(R.id.progress);

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Profile.this , EditProfile.class);
                startActivity(i);


            }
        });

        onResume();


    }

    @Override
    protected void onResume() {
        super.onResume();

            bar.setVisibility(View.VISIBLE);

            Bean b = (Bean) getApplicationContext();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.BaseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApiInterface cr = retrofit.create(AllApiInterface.class);

            Call<ProfileBean> call = cr.profile(b.userid);

            call.enqueue(new Callback<ProfileBean>() {
                @Override
                public void onResponse(Call<ProfileBean> call, Response<ProfileBean> response) {

                    if (Objects.equals(response.body().getStatus(), "1")) {

                        name.setText(response.body().getData().getFirstName());

                        gender.setText(response.body().getData().getGender());

                        email.setText(response.body().getData().getEmail());

                        city.setText(response.body().getData().getLocation());

                        mobile.setText(response.body().getData().getPhone());

                        pincode.setText(response.body().getData().getPincode());


                    } else {

                        Toast.makeText(Profile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                    bar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ProfileBean> call, Throwable t) {


                    bar.setVisibility(View.GONE);
                }
            });




    }
}
