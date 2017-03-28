package com.aceplus.samparoo.model;

import java.io.Serializable;

/**
 * Created by ESeries on 10/8/2015.
 */
public class CustomerCredit implements Serializable{
    int creditId;
    int customerId;
    String customerCreditname;
    String customerAddress;
    double creditTotalAmt;
    double creditPaidAmt;
    double creditUnpaidAmt;
    int currencyId;

    public int getCreditId() {
        return creditId;
    }

    public void setCreditId(int creditId) {
        this.creditId = creditId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCreditname() {
        return customerCreditname;
    }

    public void setCustomerCreditname(String customerCreditname) {
        this.customerCreditname = customerCreditname;
    }

    public double getCreditTotalAmt() {
        return creditTotalAmt;
    }

    public void setCreditTotalAmt(double creditTotalAmt) {
        this.creditTotalAmt = creditTotalAmt;
    }

    public double getCreditPaidAmt() {
        return creditPaidAmt;
    }

    public void setCreditPaidAmt(double creditPaidAmt) {
        this.creditPaidAmt = creditPaidAmt;
    }

    public double getCreditUnpaidAmt() {
        return creditUnpaidAmt;
    }

    public void setCreditUnpaidAmt(double creditUnpaidAmt) {
        this.creditUnpaidAmt = creditUnpaidAmt;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }
}
