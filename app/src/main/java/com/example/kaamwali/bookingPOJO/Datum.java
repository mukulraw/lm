package com.example.kaamwali.bookingPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("bai_id")
    @Expose
    private String baiId;
    @SerializedName("bai_name")
    @Expose
    private String baiName;
    @SerializedName("months")
    @Expose
    private String months;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaiId() {
        return baiId;
    }

    public void setBaiId(String baiId) {
        this.baiId = baiId;
    }

    public String getBaiName() {
        return baiName;
    }

    public void setBaiName(String baiName) {
        this.baiName = baiName;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
