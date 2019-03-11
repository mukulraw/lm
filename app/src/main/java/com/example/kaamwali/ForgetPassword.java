package com.example.kaamwali;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ForgetPassword extends AppCompatActivity {

    EditText email;


    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        email = findViewById(R.id.editText);

      //  bar = findViewById(R.id.progress);

        btn = findViewById(R.id.button3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    String e = email.getText().toString();

                    if (e.length() > 0) {

                        // bar.setVisibility(View.VISIBLE);

                        Bean bv = (Bean) getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(bv.BaseUrl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        AllApiInterface cr = retrofit.create(AllApiInterface.class);

                        Call<ForgetBean> call = cr.forgot(e);

                        call.enqueue(new Callback<ForgetBean>() {
                            @Override
                            public void onResponse(Call<ForgetBean> call, Response<ForgetBean> response) {

                                if (Objects.equals(response.body().getStatus(), "1")) {

                                    Toast.makeText(ForgetPassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    finish();

                                    email.setText("");
                                } else {

                                    Toast.makeText(ForgetPassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                               // bar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<ForgetBean> call, Throwable t) {

                                //bar.setVisibility(View.GONE);

                            }
                        });


                    } else {

                        Toast.makeText(ForgetPassword.this, "Please enter a Email", Toast.LENGTH_SHORT).show();
                    }

            }
        });





    }
}
