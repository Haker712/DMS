package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yma on 4/4/17.
 *
 * SaleTargetForCustomer
 */

public class SaleTargetForCustomer {

    /**
     * Id
     */
    @SerializedName("Id")
    @Expose
    String id;

    /**
     * FromDate
     */
    @SerializedName("FromDate")
    @Expose
    String fromDate;

    /**
     * ToDate
     */
    @SerializedName("ToDate")
    @Expose
    String toDate;

    /**
     * ToDate
     */
    @SerializedName("CustomerId")
    @Expose
    String customerId;

    /**
     * SaleManId
     */
    @SerializedName("SaleManId")
    @Expose
    String saleManId;

    /**
     * CategoryId
     */
    @SerializedName("CategoryId")
    @Expose
    String categoryId;

    /**
     * GroupCodeId
     */
    @SerializedName("GroupCodeId")
    @Expose
    String groupCodeId;

    /**
     * StockId
     */
    @SerializedName("StockId")
    @Expose
    String stockId;

    /**
     * TargetAmount
     */
    @SerializedName("TargetAmount")
    @Expose
    String targetAmount;

    /**
     * Date
     */
    @SerializedName("Date")
    @Expose
    String date;

    /**
     * Day
     */
    @SerializedName("Day")
    @Expose
    String day;

    /**
     * DateStatus
     */
    @SerializedName("DateStatus")
    @Expose
    String dateStatus;

    /**
     * InvoiceNo
     */
    @SerializedName("InvoiceNo")
    @Expose
    String invoiceNo;

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
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for fromDate
     *
     * @return fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * Setter method for fromDate
     *
     * @param fromDate fromDate
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Getter method for toDate
     *
     * @return toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * Setter method for toDate
     *
     * @param toDate toDate
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
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
     * Getter method for categoryId
     *
     * @return categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * Setter method for categoryId
     *
     * @param categoryId categoryId
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Getter method for groupCodeId
     *
     * @return groupCodeId
     */
    public String getGroupCodeId() {
        return groupCodeId;
    }

    /**
     * Setter method for groupCodeId
     *
     * @param groupCodeId groupCodeId
     */
    public void setGroupCodeId(String groupCodeId) {
        this.groupCodeId = groupCodeId;
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
     * Setter method for stockId
     *
     * @param stockId stockId
     */
    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    /**
     * Getter method for targetAmount
     *
     * @return targetAmount
     */
    public String getTargetAmount() {
        return targetAmount;
    }

    /**
     * Setter method for targetAmount
     *
     * @param targetAmount targetAmount
     */
    public void setTargetAmount(String targetAmount) {
        this.targetAmount = targetAmount;
    }

    /**
     * Getter method for date
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter method for date
     *
     * @param date date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter method for day
     *
     * @return day
     */
    public String getDay() {
        return day;
    }

    /**
     * Setter method for day
     *
     * @param day day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Getter method for dateStatus
     *
     * @return dateStatus
     */
    public String getDateStatus() {
        return dateStatus;
    }

    /**
     * Setter method for dateStatus
     *
     * @param dateStatus dateStatus
     */
    public void setDateStatus(String dateStatus) {
        this.dateStatus = dateStatus;
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
     * @param invoiceNo invoiceNo
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
