package com.aceplus.samparoo.model;

import java.io.Serializable;

/**
 * Created by yma on 2/22/17.
 *
 * DeliverItem
 */

public class DeliverItem implements Serializable {

    /**
     * deliverId
     */
    private int deliverId;

    /**
     * stock number
     */
    private String stockNo;

    /**
     * order quantity
     */
    private int orderQty;

    /**
     * received quantity
     */
    private int receivedQty;

    /**
     * price
     */
    private double SPrice;

    /**
     * productName
     */
    private String productName;

    /**
     * Getter method for deliverId
     *
     * @return deliverId
     */
    public int getDeliverId() {
        return deliverId;
    }

    /**
     * Setter method for deliverId
     *
     * @param deliverId deliver id
     */
    public void setDeliverId(int deliverId) {
        this.deliverId = deliverId;
    }

    /**
     * Getter method for stockNo
     *
     * @return stockNo
     */
    public String getStockNo() {
        return stockNo;
    }

    /**
     * Setter method for stockNo
     *
     * @param stockNo stock number
     */
    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
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
     * @param orderQty order quantity
     */
    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    /**
     * Getter method for receivedQty
     *
     * @return receivedQty
     */
    public int getReceivedQty() {
        return receivedQty;
    }

    /**
     * Setter method for receivedQty
     *
     * @param receivedQty received quantity
     */
    public void setReceivedQty(int receivedQty) {
        this.receivedQty = receivedQty;
    }

    /**
     * Getter method for SPrice
     *
     * @return SPrice
     */
    public double getSPrice() {
        return SPrice;
    }

    /**
     * Setter method for SPrice
     *
     * @param SPrice price
     */
    public void setSPrice(double SPrice) {
        this.SPrice = SPrice;
    }

    /**
     * Getter method for productName
     *
     * @return productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Setter method for product name
     *
     * @param productName product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
}
