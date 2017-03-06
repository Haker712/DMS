package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by haker on 2/10/17.
 */
public class PromotionPrice {
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("PromotionPlanId")
    @Expose
    private String promotionPlanId;
    @SerializedName("StockId")
    @Expose
    private String stockId;
    @SerializedName("FromQuantity")
    @Expose
    private String fromQuantity;
    @SerializedName("ToQuantity")
    @Expose
    private String toQuantity;
    @SerializedName("PromotionPrice")
    @Expose
    private String promotionPrice;
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

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getFromQuantity() {
        return fromQuantity;
    }

    public void setFromQuantity(String fromQuantity) {
        this.fromQuantity = fromQuantity;
    }

    public String getToQuantity() {
        return toQuantity;
    }

    public void setToQuantity(String toQuantity) {
        this.toQuantity = toQuantity;
    }

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
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
