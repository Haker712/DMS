package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yma on 3/8/17.
 *
 * DeliveryItemApi
 */

public class DeliveryItemApi {

    /**
     * deliveryId
     */
    @SerializedName("DeliveryId")
    @Expose
    String deliveryId;

    /**
     * stockId
     */
    @SerializedName("StockId")
    @Expose
    int stockId;

    /**
     * deliveryQty
     */
    @SerializedName("Quantity")
    @Expose
    int deliveryQty;

    /**
     * FOC
     */
    @SerializedName("FOC")
    @Expose
    short foc;

    /**
     * Getter method for deliveryId
     *
     * @return deliveryId
     */
    public String getDeliveryId() {
        return deliveryId;
    }

    /**
     * Setter method for deliveryId
     *
     * @param deliveryId delivery id
     */
    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    /**
     * Getter method for stockId
     *
     * @return stockId
     */
    public int getStockId() {
        return stockId;
    }

    /**
     * Setter method for stockId
     *
     * @param stockId stock id
     */
    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    /**
     * Getter method for deliveryQty
     *
     * @return deliveryQty
     */
    public int getDeliveryQty() {
        return deliveryQty;
    }

    /**
     * Setter method for deliveryQty
     *
     * @param deliveryQty delivery quantity
     */
    public void setDeliveryQty(int deliveryQty) {
        this.deliveryQty = deliveryQty;
    }

    /**
     * Getter method for foc
     *
     * @return foc
     */
    public short getFoc() {
        return foc;
    }

    /**
     * Setter method for foc
     *
     * @param foc 0 : no foc, 1 : foc
     */
    public void setFoc(short foc) {
        this.foc = foc;
    }
}
