package com.aceplus.samparoo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by yma on 2/15/17.
 *
 * PreOrder - to store pre order item
 */

public class PreOrder implements Cloneable, Serializable {

    /**
     * invoiceId
     */
    String invoiceId;

    /**
     * customerId
     */
    String customerId;

    /**
     * salePersonId
     */
    String salePersonId;

    /**
     * deviceId
     */
    String deviceId;

    /**
     * preOrderDate
     */
    String preOrderDate;

    /**
     * expectedDeliveryDate
     */
    String expectedDeliveryDate;

    /**
     * advancedPaymentAmount
     */
    Double advancedPaymentAmount;

    /**
     * netAmount
     */
    Double netAmount;

    Double taxAmount;

    private Integer locationId;

    private Double discount;

    private Double discountPer;

    private String remark;

    private String bankName;

    private String bankAccountNo;

    /**
     * Getter method of invoiceId
     *
     * @return invoiceId
     */
    public String getInvoiceId() {
        return invoiceId;
    }

    /**
     * Setter method of invoiceId
     *
     * @param invoiceId invoice id
     */
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * Getter method of customerId
     *
     * @return customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Setter method of customerId
     *
     * @param customerId customer id
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method of salePersonId
     *
     * @return salePersonId
     */
    public String getSalePersonId() {
        return salePersonId;
    }

    /**
     * Setter method of salePersonId
     *
     * @param salePersonId sale person id
     */
    public void setSalePersonId(String salePersonId) {
        this.salePersonId = salePersonId;
    }

    /**
     * Getter method of deviceId
     *
     * @return deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Setter method of deviceId
     *
     * @param deviceId device id
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Getter method of preOrderDate
     *
     * @return preOrderDate
     */
    public String getPreOrderDate() {
        return preOrderDate;
    }

    /**
     * Setter method of preOrderDate
     *
     * @param preOrderDate pre order date
     */
    public void setPreOrderDate(String preOrderDate) {
        this.preOrderDate = preOrderDate;
    }

    /**
     * Getter method of expectedDeliveryDate
     *
     * @return expectedDeliveryDate
     */
    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    /**
     * Setter method of expectedDeliveryDate
     *
     * @param expectedDeliveryDate expected delivery date
     */
    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    /**
     * Getter method of advancedPaymentAmount
     *
     * @return advancedPaymentAmount
     */
    public Double getAdvancedPaymentAmount() {
        return advancedPaymentAmount;
    }

    /**
     * Setter method of advancedPaymentAmount
     *
     * @param advancedPaymentAmount prepaid amount
     */
    public void setAdvancedPaymentAmount(Double advancedPaymentAmount) {
        this.advancedPaymentAmount = advancedPaymentAmount;
    }

    /**
     * Getter method of netAmount
     *
     * @return netAmount
     */
    public Double getNetAmount() {
        return netAmount;
    }

    /**
     * Setter method of netAmount
     *
     * @param netAmount net amount
     */
    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(Double discountPer) {
        this.discountPer = discountPer;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }
}
