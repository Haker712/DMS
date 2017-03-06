package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by haker on 2/10/17.
 */
public class VolumeDiscountItem {
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("VolumeDiscountId")
    @Expose
    private String volumeDiscountId;
    @SerializedName("FromSaleAmt")
    @Expose
    private String fromSaleAmt;
    @SerializedName("ToSaleAmt")
    @Expose
    private String toSaleAmt;
    @SerializedName("DiscountPercent")
    @Expose
    private String discountPercent;
    @SerializedName("DiscountAmount")
    @Expose
    private Double discountAmount;
    @SerializedName("DiscountPrice")
    @Expose
    private Double discountPrice;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVolumeDiscountId() {
        return volumeDiscountId;
    }

    public void setVolumeDiscountId(String volumeDiscountId) {
        this.volumeDiscountId = volumeDiscountId;
    }

    public String getFromSaleAmt() {
        return fromSaleAmt;
    }

    public void setFromSaleAmt(String fromSaleAmt) {
        this.fromSaleAmt = fromSaleAmt;
    }

    public String getToSaleAmt() {
        return toSaleAmt;
    }

    public void setToSaleAmt(String toSaleAmt) {
        this.toSaleAmt = toSaleAmt;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
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
}
