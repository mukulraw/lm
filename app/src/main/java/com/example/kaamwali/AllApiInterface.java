package com.example.kaamwali;

import com.example.kaamwali.LocationPOJO.LocationBean;
import com.example.kaamwali.LoginPOJO.LoginBean;
import com.example.kaamwali.ProfilePOJO.ProfileBean;
import com.example.kaamwali.SignupPOJO.SignupBean;
import com.example.kaamwali.UpdateprofilePOJO.UpdateBean;
import com.example.kaamwali.baiPOJO.baiBean;
import com.example.kaamwali.bannerPOJO.bannerBean;
import com.example.kaamwali.bookingPOJO.bookingBean;
import com.example.kaamwali.categoryPOJO.categoryBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AllApiInterface {


    @Multipart
    @POST("kaamwali/api/sign_up.php")
    Call<SignupBean> signup(
            @Part("first_name") String name,
            @Part("last_name") String email,
            @Part("email") String phone,
            @Part("phone") String gender,
            @Part("gender") String dob,
            @Part("password") String ddd,
            @Part("address") String dfd,
            @Part("location") String passwdsdord,
            @Part("pincode") String dads
    );


    @Multipart
    @POST("kaamwali/api/login.php")
    Call<LoginBean> login(
            @Part("phone") String phone,
            @Part("password") String password
    );


    @Multipart
    @POST("kaamwali/api/forgotPassword.php")
    Call<ForgetBean> forgot
            (@Part("email") String email);


    @Multipart
    @POST("kaamwali/api/getProfile.php")
    Call<ProfileBean> profile
            (@Part("userId") String userid);


    @GET("kaamwali/api/getLocation.php")
    Call<LocationBean> getLocations();


    @Multipart
    @POST("kaamwali/api/updateProfile.php")
    Call<UpdateBean> update(
            @Part("first_name") String name,
            @Part("last_name") String email,
            @Part("email") String phone,
            @Part("phone") String gender,
            @Part("gender") String dob,
            @Part("address") String dfd,
            @Part("location") String passwdsdord,
            @Part("pincode") String dads ,
            @Part("userId") String d
    );


    /*@Multipart
    @POST("admin/api/verifyOTP.php")
    Call<SignupResp> verifyOTP(
            @Part("userId") RequestBody userId,
            @Part("otp") RequestBody otp
    );
*/


    @GET("kaamwali/api/getBanners.php")
    Call<bannerBean> getBanners();

    @GET("kaamwali/api/getCategories.php")
    Call<categoryBean> getCategory();

    @Multipart
    @POST("kaamwali/api/filter.php")
    Call<baiBean> filter(
            @Part("userId") String userid,
            @Part("city_id") String city,
            @Part("religion") String religiom,
            @Part("hour") String hour,
            @Part("gender") String gender,
            @Part("catId") String cat,
            @Part("sort") String sort
    );

    @Multipart
    @POST("kaamwali/api/book.php")
    Call<bookBean> book(
            @Part("userId") String userid,
            @Part("baiId") String bai,
            @Part("service") String service
    );

    @Multipart
    @POST("kaamwali/api/cancelBooking.php")
    Call<bookBean> cancelBooking(
            @Part("userId") String userid,
            @Part("bookingId") String bookingId
    );

    @Multipart
    @POST("kaamwali/api/getBookings.php")
    Call<bookingBean> getBookings(
            @Part("userId") String userid,
            @Part("status") String bookingId
    );

}




