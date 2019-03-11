package com.example.kaamwali;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.relex.circleindicator.CircleIndicator;

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

    SharedPreferences pref;

    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        edit = pref.edit();

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        pager = (AutoScrollViewPager) findViewById(R.id.pager);

        pager.setOnPageChangeListener(new MyOnPageChangeListener());

        pager.setInterval(2000);

        pager.startAutoScroll();
        //pager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));

        indicator = findViewById(R.id.indicator);

        adapter = new ViewAdapter(getSupportFragmentManager(), 4);

        pager.setAdapter(adapter);

        indicator.setViewPager(pager);

        grid = findViewById(R.id.grid);

        manager = new GridLayoutManager(getApplicationContext() , 2);

        ada  = new HomeAdapter(this);

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

                edit.clear();
                edit.apply();

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

        public HomeAdapter(Context context){

            this.context = context;
        }


        @NonNull
        @Override
        public HomeAdapter.My onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.home_list_model ,viewGroup , false);
            return new My(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeAdapter.My my, int i) {

        }

        @Override
        public int getItemCount() {
            return 6;
        }

        public class My extends RecyclerView.ViewHolder {

            public My(@NonNull View itemView) {
                super(itemView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(MainActivity.this , Filter.class);
                        startActivity(i);
                    }
                });
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


            public ViewAdapter(FragmentManager fm, int tab) {
                super(fm);
            }

            @Override
            public Fragment getItem(int i) {

                if (i == 0) {

                    return new Image1();

                } else if (i == 1) {

                    return new Image2();
                }else if (i == 2){

                    return new Image3();
                }else if (i == 3){

                    return new Image4();
                }


                return null;
            }

            @Override
            public int getCount() {
                return 4;
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
    }

}
