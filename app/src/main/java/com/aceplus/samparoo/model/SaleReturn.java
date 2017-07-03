package com.aceplus.samparoo.model;

import java.io.Serializable;

/**
 * Created by yma on 2/16/17.
 *
 * SaleReturn
 */

public class SaleReturn implements Cloneable, Serializable {

    /**
     * sale return id
     */
    private String saleReturnId;

    /**
     * customer id
     */
    private int customerId;

    /**
     * location id
     */
    private int locationId;

    /**
     * net amount
     */
    private double amt;

    /**
     * return cash amount
     */
    private double payAmt;

    /**
     * pc address
     */
    private String pcAddress;

    /**
     * return date
     */
    private String returnedDate;

    /**
     * Invoice status
     */
    private String invoiceStatus;

    /**
     * Sale Man Id
     */
    private String saleManId;

    /**
     * Sale id
     */
    private String saleId;

    /**
     * Getter method for saleReturnId
     *
     * @return saleReturnId
     */
    public String getSaleReturnId() {
        return saleReturnId;
    }

    /**
     * Setter method for saleReturnId
     *
     * @param saleReturnId sale return id
     */
    public void setSaleReturnId(String saleReturnId) {
        this.saleReturnId = saleReturnId;
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
     * @param customerId customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method for locationId
     *
     * @return locationId
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * Setter method for locationId
     *
     * @param locationId location id
     */
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    /**
     * Getter method for amt
     *
     * @return amt
     */
    public double getAmt() {
        return amt;
    }

    /**
     * Setter method for amt
     *
     * @param amt net amount
     */
    public void setAmt(double amt) {
        this.amt = amt;
    }

    /**
     * Getter method for payAmt
     *
     * @return payAmt
     */
    public double getPayAmt() {
        return payAmt;
    }

    /**
     * Setter method for payAmt
     *
     * @param payAmt returned cash amount
     */
    public void setPayAmt(double payAmt) {
        this.payAmt = payAmt;
    }

    /**
     * Getter method for pcAddress
     *
     * @return pcAddress
     */
    public String getPcAddress() {
        return pcAddress;
    }

    /**
     * Setter method for pcAddress
     *
     * @param pcAddress pc address
     */
    public void setPcAddress(String pcAddress) {
        this.pcAddress = pcAddress;
    }

    /**
     * Getter method for returnedDate
     *
     * @return returnedDate
     */
    public String getReturnedDate() {
        return returnedDate;
    }

    /**
     * Setter method for returnedDate
     *
     * @param returnedDate return date
     */
    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

    /**
     * Getter method for invoice status
     *
     * @return invoiceStatus
     */
    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    /**
     * Setter invoice status
     *
     * @param invoiceStatus credit or cash
     */
    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    /**
     * Getter method for sale man id
     *
     * @return sale man id
     */
    public String getSaleManId() {
        return saleManId;
    }

    /**
     * Setter method for sale man id.
     *
     * @param saleManId sale man id
     */
    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    /**
     * Getter method for sale id
     *
     * @return saleId
     */
    public String getSaleId() {
        return saleId;
    }

    /**
     * Setter method for sale id
     *
     * @param saleId sale id
     */
    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }
}
