package com.aceplus.samparoo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by yma on 2/14/17.
 *
 * Pre Order Product
 */

public class PreOrderProduct implements Cloneable, Serializable {

    /**
     * sale order id
     */
    private String saleOrderId;

    /**
     * product id
     */
    private int productId;

    /**
     * number of order
     */
    private int orderQty;

    /**
     * product price
     */
    private double price;

    /**
     * total amount
     */
    private double totalAmt;


    private Double promotionPrice;

    private Double volumeDiscount;

    private Double volumeDiscountPer;

    private Integer exclude;

    private int promotionPlanId;

    /**
     * Getter method for saleOrderId
     *
     * @return saleOrderId
     */
    public String getSaleOrderId() {
        return saleOrderId;
    }

    /**
     * Setter method for saleOrderId
     *
     * @param saleOrderId sale order id (invoiceId)
     */
    public void setSaleOrderId(String saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    /**
     * Getter method for productId
     *
     * @return productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Setter method for productId
     *
     * @param productId id for product
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Getter method for orderQty
     *
     * @return orderQty
     */
    public int getOrderQty() {
        return orderQty;
    }

    /**
     * Setter method for orderQty
     *
     * @param orderQty number of order
     */
    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    /**
     * Getter method for price
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter method for price
     *
     * @param price product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter method for totalAmt
     *
     * @return totalAmt
     */
    public double getTotalAmt() {
        return totalAmt;
    }

    /**
     * Setter method for totalAmt
     *
     * @param totalAmt total amount
     */
    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
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

    public int getPromotionPlanId() {
        return promotionPlanId;
    }

    public void setPromotionPlanId(int promotionPlanId) {
        this.promotionPlanId = promotionPlanId;
    }
}
