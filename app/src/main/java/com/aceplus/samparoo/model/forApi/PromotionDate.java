package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by haker on 2/10/17.
 */
public class PromotionDate {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("PromotionPlanId")
    @Expose
    private String promotionPlanId;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("PromotionDate")
    @Expose
    private String promotionDate;
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
    @SerializedName("Process_Status")
    @Expose
    private String processStatus;
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
    private Object updatedDate;
    @SerializedName("UpdatedUserID")
    @Expose
    private Object updatedUserID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPromotionPlanId() {
        return promotionPlanId;
    }

    public void setPromotionPlanId(String promotionPlanId) {
        this.promotionPlanId = promotionPlanId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPromotionDate() {
        return promotionDate;
    }

    public void setPromotionDate(String promotionDate) {
        this.promotionDate = promotionDate;
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

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
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

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Object getUpdatedUserID() {
        return updatedUserID;
    }

    public void setUpdatedUserID(Object updatedUserID) {
        this.updatedUserID = updatedUserID;
    }
}
