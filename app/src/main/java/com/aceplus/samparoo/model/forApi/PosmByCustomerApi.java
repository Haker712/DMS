package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yma on 2/20/17.
 *
 * PosmForApi
 */

public class PosmByCustomerApi {

    /**
     * invoiceNo
     */
    @SerializedName("InvoiceNo")
    @Expose
    String invoiceNo;

    /**
     * InvoiceDate
     */
    @SerializedName("InvoiceDate")
    @Expose
    String invoiceDate;

    /**
     * customerId
     */
    @SerializedName("CustomerId")
    @Expose
    int customerId;

    /**
     * StockId
     */
    @SerializedName("StockId")
    @Expose
    int stockId;

    /**
     * ShopTypeId
     */
    @SerializedName("ShopTypeId")
    @Expose
    int shopTypeId;

    /**
     * saleManId
     */
    @SerializedName("SaleManId")
    @Expose
    String saleManId;

    /**
     * Quantity
     */
    @SerializedName("Quantity")
    @Expose
    int quantity;

    /**
     * Price
     */
    @SerializedName("Price")
    @Expose
    double price;

    /**
     * Getter method for invoiceNo
     *
     * @return invoiceNo
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * Setter method for invoiceNo
     *
     * @param invoiceNo invoice no
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * Getter method for invoiceDate
     *
     * @return invoiceDate
     */
    public String getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Setter method for invoiceDate
     *
     * @param invoiceDate invoice date
     */
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * Getter method for shopTypeId
     *
     * @return shopTypeId
     */
    public int getShopTypeId() {
        return shopTypeId;
    }

    /**
     * Setter method for shopTypeId
     *
     * @param shopTypeId shop type id
     */
    public void setShopTypeId(int shopTypeId) {
        this.shopTypeId = shopTypeId;
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
     * Setter method for stock id
     *
     * @param stockId stock id
     */
    public void setStockId(int stockId) {
        this.stockId = stockId;
    }


    /**
     * Getter method for customerId
     *
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter method for customerId
     *
     * @param customerId customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method for saleManId
     *
     * @return saleManId
     */
    public String getSaleManId() {
        return saleManId;
    }

    /**
     * Setter method for saleManId
     *
     * @param saleManId saleManId
     */
    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    /**
     * Getter method for quantity
     *
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter method for quantity
     *
     * @param quantity quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
     * @param price price
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
