package com.aceplus.samparoo.model.forApi;

import com.aceplus.samparoo.model.SaleReturnDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 2/16/17.
 *
 * SaleReturnApi
 */

public class SaleReturnApi {

    /**
     * InvoiceNo
     */
    @SerializedName("InvoiceNo")
    @Expose
    private String invoiceNo;

    /**
     * InvoiceDate
     */
    @SerializedName("InvoiceDate")
    @Expose
    private String invoiceDate;

    /**
     * CustomerId
     */
    @SerializedName("CustomerId")
    @Expose
    private int customerId;

    /**
     * LocationId
     */
    @SerializedName("LocationId")
    @Expose
    private int locationId;

    /**
     * PCAddress
     */
    @SerializedName("PCAddress")
    @Expose
    private String pcAddress;

    /**
     * Amount
     */
    @SerializedName("Amount")
    @Expose
    private double amount;

    /**
     * Pay Amount
     */
    @SerializedName("PayAmount")
    @Expose
    private double payAmount;

    @SerializedName("CurrencyId")
    @Expose
    private int currencyId;

    @SerializedName("Rate")
    @Expose
    private double rate;

    @SerializedName("InvoiceStatus")
    @Expose
    private String invoiceStatus;

    @SerializedName("SaleManId")
    @Expose
    private int saleManId;

    @SerializedName("SaleId")
    @Expose
    private int saleId;

    /**
     * TSaleReturnItem
     */
    @SerializedName("TSaleReturnItem")
    @Expose
    private List<SaleReturnItem> saleReturnItemList;

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
     * Getter method of invoiceDate
     *
     * @return invoiceDate
     */
    public String getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Setter method of invoiceDate
     *
     * @param invoiceDate invoice date
     */
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * Getter method of customerId
     *
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter method of customerId
     *
     * @param customerId customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method of locationId
     *
     * @return locationId
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * Setter method of locationId
     *
     * @param locationId location id
     */
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    /**
     * Getter method of pcAddress
     *
     * @return pcAddress
     */
    public String getPcAddress() {
        return pcAddress;
    }

    /**
     * Setter method of pcAddress
     *
     * @param pcAddress
     */
    public void setPcAddress(String pcAddress) {
        this.pcAddress = pcAddress;
    }

    /**
     * Getter method of amount
     *
     * @return amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Setter method of amount
     *
     * @param amount amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Getter method of payAmount
     *
     * @return payAmount
     */
    public double getPayAmount() {
        return payAmount;
    }

    /**
     * Setter method of payAmount
     *
     * @param payAmount pay amount
     */
    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * Getter method of saleReturnItemList
     *
     * @return saleReturnItemList
     */
    public List<SaleReturnItem> getSaleReturnItemList() {
        return saleReturnItemList;
    }

    /**
     * Setter method of saleReturnItemList
     *
     * @param saleReturnItemList SaleReturnItem list
     */
    public void setSaleReturnItemList(List<SaleReturnItem> saleReturnItemList) {
        this.saleReturnItemList = saleReturnItemList;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public int getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(int saleManId) {
        this.saleManId = saleManId;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }
}
