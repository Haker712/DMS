package com.aceplus.samparoo.model;

/**
 * Created by ESeries on 10/1/2015.
 */
public class CustomerLocation {
    double customerLat, customerLng;
    String customerName, customerAddress;
    int visitRecord;

    public int getVisitRecord() {
        return visitRecord;
    }

    public void setVisitRecord(int visitRecord) {
        this.visitRecord = visitRecord;
    }

    public double getCustomerLat() {
        return customerLat;
    }

    public void setCustomerLat(double customerLat) {
        this.customerLat = customerLat;
    }

    public double getCustomerLng() {
        return customerLng;
    }

    public void setCustomerLng(double customerLng) {
        this.customerLng = customerLng;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
}
