package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yma on 6/23/17.
 */

public class DeliveryPresentForApi {

    /**
     * id
     */
    @SerializedName("Id")
    @Expose
    String id;

    /**
     * SaleOrderId
     */
    @SerializedName("SaleOrderId")
    @Expose
    String saleOrderId;

    /**
     * StockId
     */
    @SerializedName("StockId")
    @Expose
    String stockId;

    /**
     * Quantity
     */
    @SerializedName("Quantity")
    @Expose
    String quantity;

    /**
     * Getter Method for id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter Method for id
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter Method for saleOrderId
     *
     * @return saleOrderId
     */
    public String getSaleOrderId() {
        return saleOrderId;
    }

    /**
     * Setter Method for saleOrderId
     *
     * @param saleOrderId
     */
    public void setSaleOrderId(String saleOrderId) {
        this.saleOrderId = saleOrderId;
    }

    /**
     * Getter Method for stockId
     *
     * @return stockId
     */
    public String getStockId() {
        return stockId;
    }

    /**
     * Setter Method for stockId
     *
     * @param stockId
     */
    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    /**
     * Getter Method for quantity
     *
     * @return quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Setter Method for Quantity
     *
     * @param quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
