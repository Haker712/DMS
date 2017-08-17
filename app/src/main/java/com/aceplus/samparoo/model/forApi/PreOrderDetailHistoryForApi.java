package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 8/8/2017.
 */

public class PreOrderDetailHistoryForApi {

    @SerializedName("SaleOrderId")
    @Expose
    private String saleOrderId;

    @SerializedName("StockId")
    @Expose
    private String stockId;

    @SerializedName("OrderQty")
    @Expose
    private String orderQty;

    @SerializedName("ReceivedQty")
    @Expose
    private String receivedQty;

    @SerializedName("SPrice")
    @Expose
    private String sPrice;

    @SerializedName("PromotionPrice")
    @Expose
    private String promotionPrice;

    @SerializedName("VolumeDiscount")
    @Expose
    private String volumeDiscount;

    @SerializedName("VolumeDiscountPer")
    @Expose
    private String volumeDiscountPer;

    @SerializedName("Exclude")
    @Expose
    private String exclude;

    @SerializedName("promotionPlanId")
    @Expose
    private String promotionPlanId;

    public String getSaleOrderId() {
        return saleOrderId;
    }

    public void setSaleOrderId(String saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(String orderQty) {
        this.orderQty = orderQty;
    }

    public String getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(String receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getsPrice() {
        return sPrice;
    }

    public void setsPrice(String sPrice) {
        this.sPrice = sPrice;
    }

    public String getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(String promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getVolumeDiscount() {
        return volumeDiscount;
    }

    public void setVolumeDiscount(String volumeDiscount) {
        this.volumeDiscount = volumeDiscount;
    }

    public String getVolumeDiscountPer() {
        return volumeDiscountPer;
    }

    public void setVolumeDiscountPer(String volumeDiscountPer) {
        this.volumeDiscountPer = volumeDiscountPer;
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public String getPromotionPlanId() {
        return promotionPlanId;
    }

    public void setPromotionPlanId(String promotionPlanId) {
        this.promotionPlanId = promotionPlanId;
    }
}
