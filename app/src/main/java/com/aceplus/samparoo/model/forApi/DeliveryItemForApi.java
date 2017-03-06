package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yma on 2/22/17.
 *
 * DeliveryItemForApi
 */

public class DeliveryItemForApi {

    /**
     * id
     */
    @SerializedName("Id")
    @Expose
    private String id;

    /**
     * saleOrderId
     */
    @SerializedName("SaleOrderId")
    @Expose
    private String saleOrderId;

    /**
     * stockNo
     */
    @SerializedName("StockNo")
    @Expose
    private String stockNo;

    /**
     * orderQty
     */
    @SerializedName("OrderQty")
    @Expose
    private String orderQty;

    /**
     * receiveQty
     */
    @SerializedName("ReceivedQty")
    @Expose
    private String receiveQty;

    /**
     * SPrice
     */
    @SerializedName("SPrice")
    @Expose
    private String SPrice;

    /**
     * focStatus
     */
    @SerializedName("FOCStatus")
    @Expose
    private String focStatus;

    /**
     * Getter method for id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for ud
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

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
     * @param saleOrderId sale order number
     */
    public void setSaleOrderId(String saleOrderId) {
        this.saleOrderId = saleOrderId;
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
    public String getOrderQty() {
        return orderQty;
    }

    /**
     * Setter method for orderQty
     *
     * @param orderQty number of items that are ordered
     */
    public void setOrderQty(String orderQty) {
        this.orderQty = orderQty;
    }

    /**
     * Getter method for receiveQty
     *
     * @return receiveQty
     */
    public String getReceiveQty() {
        return receiveQty;
    }

    /**
     * Setter method for receiveQty
     *
     * @param receiveQty receive number of item
     */
    public void setReceiveQty(String receiveQty) {
        this.receiveQty = receiveQty;
    }

    /**
     * Getter method for SPrice
     *
     * @return SPrice
     */
    public String getSPrice() {
        return SPrice;
    }

    /**
     * Setter method for SPrice
     *

     * @param SPrice sale price
     */
    public void setSPrice(String SPrice) {
        this.SPrice = SPrice;
    }

    /**
     * Getter method for focStatus
     *
     * @return focStatus
     */
    public String getFocStatus() {
        return focStatus;
    }

    /**
     * Setter method for focStatus
     *
     * @param focStatus gift item status
     */
    public void setFocStatus(String focStatus) {
        this.focStatus = focStatus;
    }
}
