package com.example.kaamwali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kaamwali.LocationPOJO.LocationBean;
import com.example.kaamwali.SignupPOJO.SignupBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Register extends AppCompatActivity {

    Button signup;

    EditText fn, ln, email, gender, address, pincode, password, confirmpassword, phone;

    ToggleSwitch toggleSwitchGender;

    int genderPositionToggle;

    String mGender, mClass = "";

    ProgressBar bar;

    Spinner spinner;
    List<String> list = new ArrayList<>();

    List<String> lid = new ArrayList<>();

    String sel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signup = findViewById(R.id.register);

        fn = findViewById(R.id.name);

        ln = findViewById(R.id.lastname);

        pincode = findViewById(R.id.pin);

        email = findViewById(R.id.email);

        toggleSwitchGender = findViewById(R.id.gender);

        address = findViewById(R.id.address);

        password = findViewById(R.id.Password);

        confirmpassword = findViewById(R.id.cp);

        bar = findViewById(R.id.progress);

        phone = findViewById(R.id.mobile);

        spinner = findViewById(R.id.spinner);

        genderPositionToggle = toggleSwitchGender.getCheckedTogglePosition();

        if (genderPositionToggle == 0) {
            mGender = "Male";
        }

        if (genderPositionToggle == 1) {
            mGender = "Female";
        }

        Bean b = (Bean) getApplicationContext();

        // progress.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiInterface cr = retrofit.create(AllApiInterface.class);

        Call<LocationBean> call = cr.getLocations();

        call.enqueue(new Callback<LocationBean>() {
            @Override
            public void onResponse(Call<LocationBean> call, Response<LocationBean> response) {

                    list.clear();
                    lid.clear();

                    list.add("Please Select a City");

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        list.add(response.body().getData().get(i).getName());
                        lid.add(response.body().getData().get(i).getId());
                    }


                    ArrayAdapter dataAdapter = new ArrayAdapter(Register.this, android.R.layout.simple_spinner_item, list);

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(dataAdapter);




                //bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<LocationBean> call, Throwable t) {
               // progress.setVisibility(View.GONE);
            }
        });




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    sel = lid.get(position - 1);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = fn.getText().toString();

                String l = ln.getText().toString();

                String e = email.getText().toString();

                String ph = phone.getText().toString();

                String p = password.getText().toString();

                String a = address.getText().toString();

                String pi = pincode.getText().toString();

                String cp = confirmpassword.getText().toString();

                if (n.length() > 0) {

                    if (l.length() > 0) {

                        if (Utils.isValidMail(e)) {

                            if (Utils.isValidMobile(ph)) {

                                if (p.length() > 0) {

                                    if (p.equals(cp)){

                                        if (a.length() > 0) {

                                            if (pi.length() > 0) {

                                                bar.setVisibility(View.VISIBLE);

                                                Bean b = (Bean) getApplicationContext();

                                                Retrofit retrofit = new Retrofit.Builder()
                                                        .baseUrl(b.BaseUrl)
                                                        .addConverterFactory(ScalarsConverterFactory.create())
                                                        .addConverterFactory(GsonConverterFactory.create())
                                                        .build();

                                                AllApiInterface cr = retrofit.create(AllApiInterface.class);

                                                Call<SignupBean> call = cr.signup(n, l, e, ph, mGender,p , a , sel , pi );
                                                call.enqueue(new Callback<SignupBean>() {
                                                    @Override
                                                    public void onResponse(Call<SignupBean> call, Response<SignupBean> response) {


                                                        if (Objects.equals(response.body().getStatus(), "1")) {

                                                            Intent i = new Intent(Register.this, Login.class);
                                                            startActivity(i);
                                                            Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                        } else {

                                                            Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }

                                                        bar.setVisibility(View.GONE);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<SignupBean> call, Throwable t) {

                                                        bar.setVisibility(View.GONE);

                                                    }
                                                });


                                            } else {

                                                Toast.makeText(Register.this, "Please enter a Pincode", Toast.LENGTH_SHORT).show();
                                            }

                                        } else {

                                            Toast.makeText(Register.this, "Please enter a Address", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else {

                                        Toast.makeText(Register.this, "Password didn't match ", Toast.LENGTH_SHORT).show();
                                    }



                                } else {

                                    Toast.makeText(Register.this, "Please enter a Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {

                                Toast.makeText(Register.this, "Please enter a Phone Number", Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            Toast.makeText(Register.this, "Please enter a EmailId", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(Register.this, "Please enter a Lastname", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Register.this, "Please enter a Firstname", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
