package com.example.kaamwali;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kaamwali.LocationPOJO.LocationBean;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Filter extends AppCompatActivity {

    Toolbar toolbar;

    SearchableSpinner spinner;

    RadioButton time1, time2, time3, low, high, male, female;

    Button conti;

    RadioGroup hour, gender, salary;

    MultiLineRadioGroup religion;

    List<String> list = new ArrayList<>();

    List<String> lid = new ArrayList<>();

    String sel = "";

    String r = "", h = "", g = "", s = "", c = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        c = getIntent().getStringExtra("cat_id");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        hour = findViewById(R.id.radio);
        gender = findViewById(R.id.rad);
        salary = findViewById(R.id.group);
        religion = findViewById(R.id.religion);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        toolbar.setTitle("Filter");

        spinner = findViewById(R.id.spinner);

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);
        time3 = findViewById(R.id.time3);

        low = findViewById(R.id.low);
        high = findViewById(R.id.high);

        conti = findViewById(R.id.conti);



        Bean b = (Bean) getApplicationContext();

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


                ArrayAdapter dataAdapter = new ArrayAdapter(Filter.this, android.R.layout.simple_spinner_item, list);

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


        hour.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int iidd = group.getCheckedRadioButtonId();

                if (iidd > -1) {
                    RadioButton rb = findViewById(iidd);

                    h = rb.getText().toString();

                }

            }
        });

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int iidd = group.getCheckedRadioButtonId();

                if (iidd > -1) {
                    RadioButton rb = findViewById(iidd);

                    g = rb.getText().toString();

                }

            }
        });

        salary.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int iidd = group.getCheckedRadioButtonId();

                if (iidd > -1) {
                    RadioButton rb = findViewById(iidd);

                    s = rb.getText().toString();

                }

            }
        });

        religion.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ViewGroup group, RadioButton button) {
                r = button.getText().toString();
            }
        });
        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sel.length() > 0) {
                    if (r.length() > 0)
                    {
                        if (h.length() > 0)
                        {
                            if (g.length() > 0)
                            {
                                if (s.length() > 0)
                                {


                                    Intent i = new Intent(Filter.this, Helper.class);
                                    i.putExtra("cat_id" , c);
                                    i.putExtra("religion" , r);
                                    i.putExtra("hour" , h);
                                    i.putExtra("gender" , g);
                                    i.putExtra("salary" , s);
                                    i.putExtra("city" , sel);
                                    startActivity(i);
                                    finish();






                                }
                                else {
                                    Toast.makeText(Filter.this, "Please select salary", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(Filter.this, "Please select a gender", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(Filter.this, "Please select an hour", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(Filter.this, "Please select a religion", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Filter.this, "Please select an area", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
