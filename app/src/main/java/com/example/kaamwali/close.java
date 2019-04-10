package com.example.kaamwali;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaamwali.bookingPOJO.Datum;
import com.example.kaamwali.bookingPOJO.bookingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class close extends Fragment {

    ProgressBar progress;
    RecyclerView grid;
    GridLayoutManager manager;
    BookingAdapter adapter;
    List<Datum> list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.opne , container , false);

        list = new ArrayList<>();

        adapter = new BookingAdapter(getContext(), list);
        manager = new GridLayoutManager(getContext(), 1);

        grid = view.findViewById(R.id.grid);
        progress = view.findViewById(R.id.progress);


        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


        loadData();



        return view;

    }

    void loadData()
    {

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.BaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiInterface cr = retrofit.create(AllApiInterface.class);

        Call<bookingBean> call = cr.getBookings(SharePreferenceUtils.getInstance().getString("userId") , "CLOSED");
        call.enqueue(new Callback<bookingBean>() {
            @Override
            public void onResponse(Call<bookingBean> call, Response<bookingBean> response) {

                assert response.body() != null;
                adapter.setData(response.body().getData());
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<bookingBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }


    class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

        Context context;
        List<Datum> list;

        BookingAdapter(Context context, List<Datum> list) {
            this.context = context;
            this.list = list;
        }

        public void setData(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.booking_list_model, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {


            final Datum item = list.get(i);

            holder.cancel.setVisibility(View.GONE);

            holder.service.setText(item.getService());
            holder.date.setText(item.getCreatedDate());
            holder.months.setText(item.getMonths());
            holder.name.setText(item.getBaiName());

            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) Objects.requireNonNull(getContext()).getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.BaseUrl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiInterface cr = retrofit.create(AllApiInterface.class);

                    Call<bookBean> call = cr.cancelBooking(SharePreferenceUtils.getInstance().getString("userId") , item.getBookingId());
                    call.enqueue(new Callback<bookBean>() {
                        @Override
                        public void onResponse(Call<bookBean> call, Response<bookBean> response) {

                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            progress.setVisibility(View.GONE);

                            loadData();

                        }

                        @Override
                        public void onFailure(Call<bookBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView service , date , months , name;
            Button cancel;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                service = itemView.findViewById(R.id.textView17);
                date = itemView.findViewById(R.id.textView18);
                months = itemView.findViewById(R.id.textView19);
                name = itemView.findViewById(R.id.textView20);
                cancel = itemView.findViewById(R.id.button5);
            }
        }
    }

}
