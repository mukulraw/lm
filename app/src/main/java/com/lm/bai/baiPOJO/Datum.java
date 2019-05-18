package com.lm.bai.baiPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("bai_id")
    @Expose
    private String baiId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("religion")
    @Expose
    private String religion;
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("salary")
    @Expose
    private String salary;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("skills")
    @Expose
    private String skills;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("image")
    @Expose
    private String image;

    public String getBaiId() {
        return baiId;
    }

    public void setBaiId(String baiId) {
        this.baiId = baiId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
