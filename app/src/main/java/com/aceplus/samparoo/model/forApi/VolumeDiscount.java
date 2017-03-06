package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by haker on 2/10/17.
 */
public class VolumeDiscount {
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("DiscountPlanNo")
    @Expose
    private String discountPlanNo;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("Sunday")
    @Expose
    private String sunday;
    @SerializedName("Monday")
    @Expose
    private String monday;
    @SerializedName("Tuesday")
    @Expose
    private String tuesday;
    @SerializedName("Wednesday")
    @Expose
    private String wednesday;
    @SerializedName("Thursday")
    @Expose
    private String thursday;
    @SerializedName("Friday")
    @Expose
    private String friday;
    @SerializedName("Saturday")
    @Expose
    private String saturday;
    @SerializedName("SHour")
    @Expose
    private String sHour;
    @SerializedName("SMinute")
    @Expose
    private String sMinute;
    @SerializedName("SShif")
    @Expose
    private String sShif;
    @SerializedName("EHour")
    @Expose
    private String eHour;
    @SerializedName("EMinute")
    @Expose
    private String eMinute;
    @SerializedName("EShif")
    @Expose
    private String eShif;
    @SerializedName("Active")
    @Expose
    private String active;
    @SerializedName("TS")
    @Expose
    private String tS;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CreatedUserID")
    @Expose
    private String createdUserID;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("UpdatedUserID")
    @Expose
    private String updatedUserID;
    @SerializedName("Exclude")
    @Expose
    private String exclude;
    @SerializedName("VolumeDiscountItem")
    @Expose
    private List<VolumeDiscountItem> volumeDiscountItem = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiscountPlanNo() {
        return discountPlanNo;
    }

    public void setDiscountPlanNo(String discountPlanNo) {
        this.discountPlanNo = discountPlanNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSHour() {
        return sHour;
    }

    public void setSHour(String sHour) {
        this.sHour = sHour;
    }

    public String getSMinute() {
        return sMinute;
    }

    public void setSMinute(String sMinute) {
        this.sMinute = sMinute;
    }

    public String getSShif() {
        return sShif;
    }

    public void setSShif(String sShif) {
        this.sShif = sShif;
    }

    public String getEHour() {
        return eHour;
    }

    public void setEHour(String eHour) {
        this.eHour = eHour;
    }

    public String getEMinute() {
        return eMinute;
    }

    public void setEMinute(String eMinute) {
        this.eMinute = eMinute;
    }

    public String getEShif() {
        return eShif;
    }

    public void setEShif(String eShif) {
        this.eShif = eShif;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTS() {
        return tS;
    }

    public void setTS(String tS) {
        this.tS = tS;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUserID() {
        return createdUserID;
    }

    public void setCreatedUserID(String createdUserID) {
        this.createdUserID = createdUserID;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Object getUpdatedUserID() {
        return updatedUserID;
    }

    public void setUpdatedUserID(String updatedUserID) {
        this.updatedUserID = updatedUserID;
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public List<VolumeDiscountItem> getVolumeDiscountItem() {
        return volumeDiscountItem;
    }

    public void setVolumeDiscountItem(List<VolumeDiscountItem> volumeDiscountItem) {
        this.volumeDiscountItem = volumeDiscountItem;
    }
}
