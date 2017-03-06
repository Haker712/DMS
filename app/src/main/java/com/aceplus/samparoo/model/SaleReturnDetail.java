package com.aceplus.samparoo.model;

import java.io.Serializable;

/**
 * Created by yma on 2/16/17.
 *
 * SaleReturnDetail
 */

public class SaleReturnDetail implements Cloneable, Serializable {

    /**
     * saleReturnId
     */
    private String saleReturnId;

    /**
     * productId
     */
    private String productId;

    /**
     * price
     */
    private double price;

    /**
     * quantity
     */
    private int quantity;

    /**
     * remark
     */
    private String remark;

    /**
     * Getter method of
     *
     * @return saleReturnId
     */
    public String getSaleReturnId() {
        return saleReturnId;
    }

    /**
     * Setter method of saleReturnId
     *
     * @param saleReturnId sale return id
     */
    public void setSaleReturnId(String saleReturnId) {
        this.saleReturnId = saleReturnId;
    }

    /**
     * Getter method of productId
     *
     * @return productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Setter method of productId
     *
     * @param productId product id
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Getter method of quantity
     *
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter method of quantity
     *
     * @param quantity quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter method of remark
     *
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Setter method of remark
     *
     * @param remark remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * Getter method of price
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter method of price
     *
     * @param price price
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
