package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phonelin on 2/13/17.
 */
public class InvoiceDetail {

    @SerializedName("tsale_id")
    @Expose
    private String tsaleId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("discount_amt")
    @Expose
    private Double discountAmt;
    @SerializedName("Amt")
    @Expose
    private Double amt;
    @SerializedName("discount_percent")
    @Expose
    private Double discountPercent;
    @SerializedName("s_price")
    @Expose
    private Double s_price;
    @SerializedName("p_price")
    @Expose
    private Double p_price;
    @SerializedName("promotion_price")
    @Expose
    private Double promotionPrice;
    @SerializedName("promotion_plan_id")
    @Expose
    private Double promotion_plan_id;
    @SerializedName("volume_discount_percent")
    @Expose
    private Double volumeDiscountPercent;
    @SerializedName("exclude")
    @Expose
    private int exclude;

    public String getTsaleId() {
        return tsaleId;
    }

    public void setTsaleId(String tsaleId) {
        this.tsaleId = tsaleId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(Double discountAmt) {
        this.discountAmt = discountAmt;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getS_price() {
        return s_price;
    }

    public void setS_price(Double s_price) {
        this.s_price = s_price;
    }

    public Double getP_price() {
        return p_price;
    }

    public void setP_price(Double p_price) {
        this.p_price = p_price;
    }

    public Double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Double getPromotion_plan_id() {
        return promotion_plan_id;
    }

    public void setPromotion_plan_id(Double promotion_plan_id) {
        this.promotion_plan_id = promotion_plan_id;
    }

    public Double getVolumeDiscountPercent() {
        return volumeDiscountPercent;
    }

    public void setVolumeDiscountPercent(Double volumeDiscountPercent) {
        this.volumeDiscountPercent = volumeDiscountPercent;
    }

    public int getExclude() {
        return exclude;
    }

    public void setExclude(int exclude) {
        this.exclude = exclude;
    }
}
