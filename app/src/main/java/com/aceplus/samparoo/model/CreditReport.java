package com.aceplus.samparoo.model;

/**
 * Created by ESeries on 12/2/2015.
 */
public class CreditReport {
    String ccInvoiceId;
    String date;
    String cutomerName;
    String customerAddress;
    double totalAmount;
    double paidAmount;
    double creditAmount;

    public String getCcInvoiceId() {
        return ccInvoiceId;
    }

    public void setCcInvoiceId(String ccInvoiceId) {
        this.ccInvoiceId = ccInvoiceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCutomerName() {
        return cutomerName;
    }

    public void setCutomerName(String cutomerName) {
        this.cutomerName = cutomerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }
}
