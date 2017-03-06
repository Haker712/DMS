package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yma on 2/15/17.
 *
 * PreOrderDetailApi - retrieve data from api
 */

public class PreOrderDetailApi {

    /**
     * sale order id
     */
    @SerializedName("sale_order_id")
    @Expose
    private String saleOrderId;

    /**
     * product id
     */
    @SerializedName("product_id")
    @Expose
    private int productId;

    /**
     * number of order
     */
    @SerializedName("qty")
    @Expose
    private double qty;

    @SerializedName("promotion_price")
    @Expose
    private Double promotionPrice;
    @SerializedName("volume_discount")
    @Expose
    private Double volumeDiscount;
    @SerializedName("volume_discount_per")
    @Expose
    private Double volumeDiscountPer;
    @SerializedName("exclude")
    @Expose
    private Integer exclude;

    /**
     * Getter method of saleOrderId
     *
     * @return saleOrderId
     */
    public String getSaleOrderId() {
        return saleOrderId;
    }

    /**
     * Setter method of saleOrderId
     *
     * @param saleOrderId sale order id
     */
    public void setSaleOrderId(String saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    /**
     * Getter method of product id
     *
     * @return productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Setter method of productId
     *
     * @param productId product id
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Getter method of qty
     *
     * @return qty
     */
    public double getQty() {
        return qty;
    }

    /**
     * Setter method of qty
     *
     * @param qty number of order
     */
    public void setQty(double qty) {
        this.qty = qty;
    }

    public Double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Double getVolumeDiscount() {
        return volumeDiscount;
    }

    public void setVolumeDiscount(Double volumeDiscount) {
        this.volumeDiscount = volumeDiscount;
    }

    public Double getVolumeDiscountPer() {
        return volumeDiscountPer;
    }

    public void setVolumeDiscountPer(Double volumeDiscountPer) {
        this.volumeDiscountPer = volumeDiscountPer;
    }

    public Integer getExclude() {
        return exclude;
    }

    public void setExclude(Integer exclude) {
        this.exclude = exclude;
    }
}
