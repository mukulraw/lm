package com.example.kaamwali;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RateCard extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView grid;

    GridLayoutManager manager;

    RateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_card);

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

        toolbar.setTitle("Rate Card");

        grid = findViewById(R.id.grid);

        adapter = new RateAdapter(this);

        manager = new GridLayoutManager(getApplicationContext(), 1);

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

    }


    public class RateAdapter extends RecyclerView.Adapter<RateAdapter.My> {

        Context context;

        public RateAdapter(Context context) {

            this.context = context;
        }


        @NonNull
        @Override
        public RateAdapter.My onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


            View view = LayoutInflater.from(context).inflate(R.layout.rate_list_model, viewGroup, false);
            return new My(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RateAdapter.My my, int i) {

        }

        @Override
        public int getItemCount() {
            return 8;
        }

        public class My extends RecyclerView.ViewHolder {

            public My(@NonNull View itemView) {
                super(itemView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent i = new Intent(RateCard.this, Help.class);
                        startActivity(i);
                    }
                });
            }
        }
    }


}
