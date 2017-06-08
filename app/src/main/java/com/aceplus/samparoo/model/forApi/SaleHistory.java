package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 4/11/17.
 *
 * SaleHistory
 */

public class SaleHistory {

    /**
     * number
     */
    @SerializedName("num")
    @Expose
    private String number;

    /**
     * id
     */
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * date
     */
    @SerializedName("date")
    @Expose
    private String date;

    /**
     * customer_id
     */
    @SerializedName("customer_id")
    @Expose
    private String customerId;

    /**
     * total_amt
     */
    @SerializedName("total_amt")
    @Expose
    private String totalAmt;

    /**
     * total_refund_amt
     */
    @SerializedName("total_pay_amt")
    @Expose
    private String totalPayAmt;

    /**
     * number
     */
    @SerializedName("total_refund_amt")
    @Expose
    private String totalRefundAmt;

    /**
     * saleperson_id
     */
    @SerializedName("saleperson_id")
    @Expose
    private String salepersonId;

    /**
     * location_code
     */
    @SerializedName("location_code")
    @Expose
    private String locationCode;

    /**
     * device_id
     */
    @SerializedName("device_id")
    @Expose
    private String deviceId;

    @SerializedName("invoice_status")
    @Expose
    private String invoiceStatus;

    /**
     * invoice_detail
     */
    @SerializedName("invoice_detail")
    @Expose
    private List<SaleHistoryDetail> saleHistoryDetailList;

    /**
     * Getter method for number
     *
     * @return number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Setter method for number
     *
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
    }

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
     * @param id
     */
    public void setId(String id) {
        this.id = id;
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
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter method for customerId
     *
     * @return customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Setter method for customerId
     *
     * @param customerId
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method for totalAmt
     *
     * @return totalAmt
     */
    public String getTotalAmt() {
        return totalAmt;
    }

    /**
     * Setter method for totalAmt
     *
     * @param totalAmt
     */
    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    /**
     * Getter method for totalPayAmt
     *
     * @return totalPayAmt
     */
    public String getTotalPayAmt() {
        return totalPayAmt;
    }

    /**
     * Setter method for totalPayAmt
     *
     * @param totalPayAmt
     */
    public void setTotalPayAmt(String totalPayAmt) {
        this.totalPayAmt = totalPayAmt;
    }

    /**
     * Getter method for totalRefundAmt
     *
     * @return totalRefundAmt
     */
    public String getTotalRefundAmt() {
        return totalRefundAmt;
    }

    /**
     * Setter method for totalRefundAmt
     *
     * @param totalRefundAmt
     */
    public void setTotalRefundAmt(String totalRefundAmt) {
        this.totalRefundAmt = totalRefundAmt;
    }

    /**
     * Getter method for salepersonId
     *
     * @return salepersonId
     */
    public String getSalepersonId() {
        return salepersonId;
    }

    /**
     * Setter method for salepersonId
     *
     * @param salepersonId
     */
    public void setSalepersonId(String salepersonId) {
        this.salepersonId = salepersonId;
    }

    /**
     * Getter method for locationCode
     *
     * @return locationCode
     */
    public String getLocationCode() {
        return locationCode;
    }

    /**
     * Setter method for locationCode
     *
     * @param locationCode
     */
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    /**
     * Getter method for deviceId
     *
     * @return deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Setter method for deviceId
     *
     * @param deviceId
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    /**
     * Getter method for saleHistoryDetailList
     *
     * @return saleHistoryDetailList
     */
    public List<SaleHistoryDetail> getSaleHistoryDetailList() {
        return saleHistoryDetailList;
    }

    /**
     * Setter method for sale history detail list
     *
     * @param saleHistoryDetailList
     */
    public void setSaleHistoryDetailList(List<SaleHistoryDetail> saleHistoryDetailList) {
        this.saleHistoryDetailList = saleHistoryDetailList;
    }
}
