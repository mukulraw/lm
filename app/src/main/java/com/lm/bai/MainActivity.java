package com.lm.bai;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lm.bai.bannerPOJO.Datum;
import com.lm.bai.bannerPOJO.bannerBean;
import com.lm.bai.categoryPOJO.categoryBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;

    Toolbar toolbar;

    TextView profile, home, booking, gallery, about , rate , share , feedback , terms ,privacy , paymnet  , contact , logout , cha;

    ViewAdapter adapter;

    AutoScrollViewPager pager;

    CircleIndicator indicator;

    RecyclerView grid;

    GridLayoutManager manager;

    HomeAdapter ada;

    TextView name;

    ProgressBar progress;


    List<com.lm.bai.categoryPOJO.Datum> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();

        name = findViewById(R.id.name);

        toolbar = findViewById(R.id.toolbar);

        progress = findViewById(R.id.progress);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);

        pager = (AutoScrollViewPager) findViewById(R.id.pager);

        pager.setOnPageChangeListener(new MyOnPageChangeListener());

        pager.setInterval(2000);

        pager.startAutoScroll();
        //pager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));

        indicator = findViewById(R.id.indicator);


        grid = findViewById(R.id.grid);

        manager = new GridLayoutManager(getApplicationContext() , 2);

        ada  = new HomeAdapter(this , list);

        grid.setLayoutManager(manager);

        grid.setAdapter(ada);

        home = findViewById(R.id.home);

        profile = findViewById(R.id.profile);

        booking = findViewById(R.id.booking);

        gallery = findViewById(R.id.home);

        rate = findViewById(R.id.ratecard);

        share = findViewById(R.id.share);

        feedback = findViewById(R.id.feedback);

        terms = findViewById(R.id.terms);

        privacy = findViewById(R.id.privacy);

        paymnet = findViewById(R.id.payment);

        contact = findViewById(R.id.contact);

        logout = findViewById(R.id.Logout);

        cha = findViewById(R.id.changepassword);

        cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this , ChangePassword.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this , Login.class);

SharePreferenceUtils.getInstance().deletePref();

                startActivity(i);
                finishAffinity();

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this , MainActivity.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);
                finishAffinity();

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this , Profile.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);


            }
        });



        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this , MyBooking.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);


            }
        });



        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this , RateCard.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);


            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this , Share.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);


            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this , Feedback.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);


            }
        });


        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this , Contact.class);
                startActivity(i);
                drawer.closeDrawer(GravityCompat.START);


            }
        });




    }





    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.My>{

        Context context;
        List<com.lm.bai.categoryPOJO.Datum> list = new ArrayList<>();

        public HomeAdapter(Context context , List<com.lm.bai.categoryPOJO.Datum> list){
            this.context = context;
            this.list = list;
        }

        public void setData(List<com.lm.bai.categoryPOJO.Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public HomeAdapter.My onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.home_list_model ,viewGroup , false);
            return new My(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeAdapter.My holder, int i) {

            final com.lm.bai.categoryPOJO.Datum item = list.get(i);


            holder.title.setText(item.getName());

            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
            ImageLoader loader = ImageLoader.getInstance();
            loader.displayImage(item.getImage() , holder.image , options);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(MainActivity.this , Filter.class);
                    i.putExtra("cat_id" , item.getId());
                    startActivity(i);
                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class My extends RecyclerView.ViewHolder {

            ImageView image;
            TextView title;

            public My(@NonNull View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.image);
                title = itemView.findViewById(R.id.title);


            }
        }
    }
        @Override
        public void onBackPressed() {

            if (drawer.isDrawerOpen(GravityCompat.START)) {

                drawer.closeDrawer(GravityCompat.START);

            } else {
                super.onBackPressed();
            }

        }


        public class ViewAdapter extends FragmentStatePagerAdapter {

        List<Datum> blist = new ArrayList<>();

            public ViewAdapter(FragmentManager fm, List<Datum> blist) {
                super(fm);
                this.blist = blist;
            }

            @Override
            public Fragment getItem(int i) {

                Image1 frag = new Image1();
                Bundle b = new Bundle();
                b.putString("url" , blist.get(i).getUrl());
                frag.setArguments(b);
                return frag;

            }

            @Override
            public int getCount() {
                return blist.size();
            }
        }












    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
           /* indexText.setText(new StringBuilder().append((position) % ListUtils.getSize(imageIdList) + 1).append("/")
                    .append(ListUtils.getSize(imageIdList)));*/
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // stop auto scroll when onPause
        pager.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        // start auto scroll when onResume
        pager.startAutoScroll();

        name.setText(SharePreferenceUtils.getInstance().getString("name"));

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiInterface cr = retrofit.create(AllApiInterface.class);

        Call<bannerBean> call = cr.getBanners();

        call.enqueue(new Callback<bannerBean>() {
            @Override
            public void onResponse(Call<bannerBean> call, Response<bannerBean> response) {

                adapter = new ViewAdapter(getSupportFragmentManager(), response.body().getData());

                pager.setAdapter(adapter);

                indicator.setViewPager(pager);

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<bannerBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

        progress.setVisibility(View.VISIBLE);

        Call<categoryBean> call1 = cr.getCategory();

        call1.enqueue(new Callback<categoryBean>() {
            @Override
            public void onResponse(Call<categoryBean> call, Response<categoryBean> response) {

                ada.setData(response.body().getData());

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<categoryBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.jobs)
        {
            Intent intent = new Intent(MainActivity.this , Jobs.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
