package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yma on 2/20/17.
 *
 * PosmForApi
 */

public class PosmForApi {

    /**
     * id
     */
    @SerializedName("Id")
    @Expose
    String id;

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
     * ShopTypeId
     */
    @SerializedName("ShopTypeId")
    @Expose
    String shopTypeId;

    /**
     * StockId
     */
    @SerializedName("StockId")
    @Expose
    String stockId;

    /**
     * Getter method for id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for id
     *
     * @param id id no
     */
    public void setId(String id) {
        this.id = id;
    }

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
    public String getShopTypeId() {
        return shopTypeId;
    }

    /**
     * Setter method for shopTypeId
     *
     * @param shopTypeId shop type id
     */
    public void setShopTypeId(String shopTypeId) {
        this.shopTypeId = shopTypeId;
    }

    /**
     * Getter method for stockId
     *
     * @return stockId
     */
    public String getStockId() {
        return stockId;
    }

    /**
     * Setter method for stock id
     *
     * @param stockId stock id
     */
    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
}
