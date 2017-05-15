package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 2/13/17.
 */
public class Invoice {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("total_amt")
    @Expose
    private Double totalAmt;
    @SerializedName("total_qty")
    @Expose
    private Integer totalQty;
    @SerializedName("total_discount_amt")
    @Expose
    private Double totalDiscountAmt;
    @SerializedName("total_pay_amt")
    @Expose
    private Double totalPayAmt;
    @SerializedName("total_refund_amt")
    @Expose
    private Double totalRefundAmt;
    @SerializedName("receipt_person")
    @Expose
    private String receiptPerson;
    @SerializedName("invoice_detail")
    @Expose
    private List<InvoiceDetail> invoiceDetail = null;
    @SerializedName("saleperson_id")
    @Expose
    private Integer salepersonId;
    @SerializedName("location_code")
    @Expose
    private int locationCode;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("invoice_time")
    @Expose
    private String invoiceTime;
    @SerializedName("currency_id")
    @Expose
    private int currencyId;
    @SerializedName("invoice_status")
    @Expose
    private String invoiceStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Double getTotalDiscountAmt() {
        return totalDiscountAmt;
    }

    public void setTotalDiscountAmt(Double totalDiscountAmt) {
        this.totalDiscountAmt = totalDiscountAmt;
    }

    public Double getTotalPayAmt() {
        return totalPayAmt;
    }

    public void setTotalPayAmt(Double totalPayAmt) {
        this.totalPayAmt = totalPayAmt;
    }

    public Double getTotalRefundAmt() {
        return totalRefundAmt;
    }

    public void setTotalRefundAmt(Double totalRefundAmt) {
        this.totalRefundAmt = totalRefundAmt;
    }

    public String getReceiptPerson() {
        return receiptPerson;
    }

    public void setReceiptPerson(String receiptPerson) {
        this.receiptPerson = receiptPerson;
    }

    public List<InvoiceDetail> getInvoiceDetail() {
        return invoiceDetail;
    }

    public void setInvoiceDetail(List<InvoiceDetail> invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public Integer getSalepersonId() {
        return salepersonId;
    }

    public void setSalepersonId(Integer salepersonId) {
        this.salepersonId = salepersonId;
    }

    public int getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(int locationCode) {
        this.locationCode = locationCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(String invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
}
