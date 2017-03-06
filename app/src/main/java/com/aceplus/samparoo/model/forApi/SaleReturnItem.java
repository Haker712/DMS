package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yma on 2/16/17.
 *
 * SaleReturnItem
 */

public class SaleReturnItem {

    /**
     * InvoiceNo
     */
    @SerializedName("InvoiceNo")
    @Expose
    private String invoiceNo;

    /**
     * StockId
     */
    @SerializedName("StockId")
    @Expose
    private String stockId;

    /**
     * Price
     */
    @SerializedName("Price")
    @Expose
    private double price;

    /**
     * Quantity
     */
    @SerializedName("Quantity")
    @Expose
    private int quantity;

    /**
     * Getter method of invoiceNo
     *
     * @return invoiceNo
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * Setter method of invoiceNo
     *
     * @param invoiceNo invoice no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * Getter method of stockId
     *
     * @return stockId
     */
    public String getStockId() {
        return stockId;
    }

    /**
     * Setter method of stockId
     *
     * @param stockId stock id
     */
    public void setStockId(String stockId) {
        this.stockId = stockId;
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
}
