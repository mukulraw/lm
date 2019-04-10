package com.example.kaamwali;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.service.autofill.RegexValidator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaamwali.LoginPOJO.LoginBean;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login extends AppCompatActivity {

    Button login;

    TextView register , forget;

    EditText phone , passwrd;

    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = findViewById(R.id.login);

        register = findViewById(R.id.signup);

        phone = findViewById(R.id.mobile);

        passwrd = findViewById(R.id.pass);

        bar = findViewById(R.id.progress);

        forget = findViewById(R.id.forget);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login.this , ForgetPassword.class);
                startActivity(i);


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final String ph = phone.getText().toString();
                    final String p = passwrd.getText().toString();

                    if (Utils.isValidMobile(ph)){

                        if (p.length()>0){

                            bar.setVisibility(View.VISIBLE);

                            Bean b = (Bean) getApplicationContext();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.BaseUrl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApiInterface cr = retrofit.create(AllApiInterface.class);

                            Call<LoginBean> call = cr.login(ph , p);
                            call.enqueue(new Callback<LoginBean>() {
                                @Override
                                public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {

                                    if (Objects.equals(response.body().getStatus() , "1")){

                                        Bean b = (Bean) getApplicationContext();

                                        SharePreferenceUtils.getInstance().saveString("phone" , ph);
                                        SharePreferenceUtils.getInstance().saveString("password" , p);
                                        SharePreferenceUtils.getInstance().saveString("userId" , response.body().getData().getUserId());
                                        SharePreferenceUtils.getInstance().saveString("name" , response.body().getData().getFirstName() + " " + response.body().getData().getLastName());

                                        Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(Login.this , MainActivity.class);
                                        startActivity(i);


                                    }else {

                                        Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }


                                    bar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onFailure(Call<LoginBean> call, Throwable t) {

                                    bar.setVisibility(View.GONE);
                                }
                            });


                        } else {
                            Toast.makeText(Login.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                        }

                    }else {

                        Toast.makeText(Login.this, "Invalid Phone", Toast.LENGTH_SHORT).show();
                    }







            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Login.this , Register.class);
                startActivity(i);
                finish();
            }
        });
    }
}
