package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yma on 3/15/17.
 *
 * CreditForApi
 */

public class CreditForApi {

    /**
     * id
     */
    @SerializedName("Id")
    @Expose
    int id;

    /**
     * invoiceNo
     */
    @SerializedName("InvoiceNo")
    @Expose
    String invoiceNo;

    /**
     * invoiceDate
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
     * amount
     */
    @SerializedName("Amount")
    @Expose
    double amount;

    /**
     * payAmount
     */
    @SerializedName("PayAmount")
    @Expose
    double payAmount;

    /**
     * firstPayAmount
     */
    @SerializedName("FirstPayAmount")
    @Expose
    double firstPayAmount;

    /**
     * extraAmount
     */
    @SerializedName("ExtraAmount")
    @Expose
    double extraAmount;

    /**
     * refund
     */
    @SerializedName("Refund")
    @Expose
    double refund;

    /**
     * saleStatus
     */
    @SerializedName("SaleStatus")
    @Expose
    String saleStatus;

    /**
     * invoiceStatus
     */
    @SerializedName("InvoiceStatus")
    @Expose
    String invoiceStatus;

    /**
     * saleManId
     */
    @SerializedName("SaleManId")
    @Expose
    String saleManId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public double getFirstPayAmount() {
        return firstPayAmount;
    }

    public void setFirstPayAmount(double firstPayAmount) {
        this.firstPayAmount = firstPayAmount;
    }

    public double getExtraAmount() {
        return extraAmount;
    }

    public void setExtraAmount(double extraAmount) {
        this.extraAmount = extraAmount;
    }

    public double getRefund() {
        return refund;
    }

    public void setRefund(double refund) {
        this.refund = refund;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }
}
