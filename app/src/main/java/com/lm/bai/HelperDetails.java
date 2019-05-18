package com.lm.bai;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HelperDetails extends AppCompatActivity {

    ImageView image;
    TextView name , service , language , skills , age , gender , marital , hours , salary;
    Button book;
    ImageButton back;
    String na , se , la , sk , ag  ,ge , ma , ho , sa , im , id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_details);

        na = getIntent().getStringExtra("na");
        se = getIntent().getStringExtra("se");
        la = getIntent().getStringExtra("la");
        sk = getIntent().getStringExtra("sk");
        ag = getIntent().getStringExtra("ag");
        ge = getIntent().getStringExtra("ge");
        ma = getIntent().getStringExtra("ma");
        ho = getIntent().getStringExtra("ho");
        sa = getIntent().getStringExtra("sa");
        im = getIntent().getStringExtra("im");
        id = getIntent().getStringExtra("id");

        name = findViewById(R.id.textView4);
        service = findViewById(R.id.service);
        language = findViewById(R.id.language);
        skills = findViewById(R.id.skills);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        marital = findViewById(R.id.marital);
        hours = findViewById(R.id.hours);
        salary = findViewById(R.id.salary);
        image = findViewById(R.id.imageView3);
        book = findViewById(R.id.button4);
        back = findViewById(R.id.imageButton);

        name.setText(na);
        service.setText(se);
        language.setText(la);
        skills.setText(sk);
        age.setText(ag);
        gender.setText(ge);
        marital.setText(ma);
        hours.setText(ho);
        salary.setText(sa);

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true).resetViewBeforeLoading(false).build();
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(im , image , options);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bean b = (Bean) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.BaseUrl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApiInterface cr = retrofit.create(AllApiInterface.class);

                Call<bookBean> call = cr.book(SharePreferenceUtils.getInstance().getString("userId") , id , se);

                call.enqueue(new Callback<bookBean>() {
                    @Override
                    public void onResponse(Call<bookBean> call, Response<bookBean> response) {

                        if (response.body().getStatus().equals("1"))
                        {
                            Dialog dialog = new Dialog(HelperDetails.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.booking_dialog);
                            dialog.setCancelable(true);
                            dialog.show();

                            TextView bid = dialog.findViewById(R.id.textView11);
                            bid.setText(response.body().getBookingId());

                        }
                        else
                        {
                            Toast.makeText(HelperDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<bookBean> call, Throwable t) {

                    }
                });

            }
        });

    }
}
