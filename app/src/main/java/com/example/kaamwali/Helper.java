package com.example.kaamwali;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kaamwali.baiPOJO.Datum;
import com.example.kaamwali.baiPOJO.baiBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Helper extends AppCompatActivity {

    Toolbar toolbar;

    ImageView filter;

    String r, h, g, s, c , sel;

    ProgressBar progress;

    RecyclerView grid;
    SharedPreferences pref;
    HelperAdapter adapter;
    GridLayoutManager manager;

    List<Datum> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);

        list = new ArrayList<>();
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        r = getIntent().getStringExtra("religion");
        h = getIntent().getStringExtra("hour");
        g = getIntent().getStringExtra("gender");
        s = getIntent().getStringExtra("salary");
        c = getIntent().getStringExtra("cat_id");
        sel = getIntent().getStringExtra("city");

        filter = findViewById(R.id.filter);

        grid = findViewById(R.id.grid);

        progress = findViewById(R.id.progress);
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

        adapter = new HelperAdapter(this , list);
        manager = new GridLayoutManager(this , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        toolbar.setTitle("Helper List");

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Helper.this, Filter.class);
                i.putExtra("cat_id" , c);
                startActivity(i);
                finish();

            }
        });



        progress.setVisibility(View.VISIBLE);
        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiInterface cr = retrofit.create(AllApiInterface.class);


        Call<baiBean> call = cr.filter(pref.getString("userId" , "") , sel , r , h , g , c);

        call.enqueue(new Callback<baiBean>() {
            @Override
            public void onResponse(Call<baiBean> call, Response<baiBean> response) {


                adapter.setData(response.body().getData());

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<baiBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

    class HelperAdapter extends RecyclerView.Adapter<HelperAdapter.ViewHolder>
    {
        Context context;
        List<Datum> list = new ArrayList<>();

        public HelperAdapter(Context context , List<Datum> list)
        {
            this.context = context;
            this.list = list;
        }

        public void setData(List<Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.helper_list_model , viewGroup , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

            Datum item = list.get(i);

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getImage() , holder.image , options);

            holder.name.setText(item.getName());
            holder.cat.setText(item.getCategoryId());
            holder.age.setText(item.getAge() + " yrs old");


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            ImageView image;
            TextView name , cat , age;
            Button book;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.imageView);
                name = itemView.findViewById(R.id.textView);
                cat = itemView.findViewById(R.id.textView2);
                age = itemView.findViewById(R.id.textView3);
                book = itemView.findViewById(R.id.button2);

            }
        }
    }

}
