package com.lm.bai;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MyBooking extends AppCompatActivity {

    Toolbar toolbar;

    PagerAdapter pagerAdapter;

    ViewPager viewPager;

    TabLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);

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

        toolbar.setTitle("My Bookings");


        viewPager = findViewById(R.id.pager);
        layout = findViewById(R.id.tab);


        layout.addTab(layout.newTab().setText("OPEN"));
        layout.addTab(layout.newTab().setText("CLOSE"));
        layout.addTab(layout.newTab().setText("CANCEL"));

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(pagerAdapter);
        layout.setupWithViewPager(viewPager);

        layout.getTabAt(0).setText("OPEN");
        layout.getTabAt(1).setText("CLOSE");
        layout.getTabAt(2).setText("CANCEL");



    }


    public class PagerAdapter extends FragmentStatePagerAdapter {


        public PagerAdapter(FragmentManager fm, int list) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            if (i == 0) {

                return new Open();
            } else if (i == 1) {
                return new close();
            }else if (i == 2){
                return new Cancel();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
