package com.aceplus.samparoo.model;

import java.io.Serializable;

/**
 * Created by DELL on 1/3/2018.
 */

public class SaleCancelItem implements Serializable{

    String customerName;

    String saleInvoiceNo;

    String date;

    int totalSaleQty;

    Double totalSaleAmt;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSaleInvoiceNo() {
        return saleInvoiceNo;
    }

    public void setSaleInvoiceNo(String saleInvoiceNo) {
        this.saleInvoiceNo = saleInvoiceNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalSaleQty() {
        return totalSaleQty;
    }

    public void setTotalSaleQty(int totalSaleQty) {
        this.totalSaleQty = totalSaleQty;
    }

    public Double getTotalSaleAmt() {
        return totalSaleAmt;
    }

    public void setTotalSaleAmt(Double totalSaleAmt) {
        this.totalSaleAmt = totalSaleAmt;
    }
}
