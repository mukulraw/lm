package com.example.kaamwali;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.kaamwali.LocationPOJO.LocationBean;

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

    Spinner spinner;

    RadioButton hindu, muslims, others, christian, time1, time2, time3, low, high, male, female;

    Button conti;

    List<String> list = new ArrayList<>();

    List<String> lid = new ArrayList<>();

    String sel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        hindu = findViewById(R.id.checkbox1);
        muslims = findViewById(R.id.checkbox2);
        christian = findViewById(R.id.checkbox3);
        others = findViewById(R.id.checkbox4);

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);
        time3 = findViewById(R.id.time3);

        low = findViewById(R.id.low);
        high = findViewById(R.id.high);

        conti = findViewById(R.id.conti);

        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(Filter.this , Helper.class);
                startActivity(i);
                finish();
            }
        });


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









    }
}
