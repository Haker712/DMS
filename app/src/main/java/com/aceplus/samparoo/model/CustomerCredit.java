package com.aceplus.samparoo.model;

/**
 * Created by ESeries on 10/8/2015.
 */
public class CustomerCredit {
    String creditId;
    String customerId;
    String customerCreditname;
    String customerAddress;
    double creditTotalAmt;
    double creditPaidAmt;
    double creditUnpaidAmt;

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
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
}
