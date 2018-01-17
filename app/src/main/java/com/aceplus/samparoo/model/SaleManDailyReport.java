package com.aceplus.samparoo.model;

/**
 * Created by DELL on 1/12/2018.
 */

public class SaleManDailyReport {

    String saleMan;

    String routeName;

    String date;

    String startTime;

    String endTime;

    double saleAmt;

    double returnAmt;

    double cashReceive;

    double netAmt;

    int customerCount;

    int saleCount;

    int orderCount;

    int returnCount;

    int cashReceiveCount;

    int notVisitCount;

    public String getSaleMan() {
        return saleMan;
    }

    public void setSaleMan(String saleMan) {
        this.saleMan = saleMan;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getSaleAmt() {
        return saleAmt;
    }

    public void setSaleAmt(double saleAmt) {
        this.saleAmt = saleAmt;
    }

    public double getReturnAmt() {
        return returnAmt;
    }

    public void setReturnAmt(double returnAmt) {
        this.returnAmt = returnAmt;
    }

    public double getCashReceive() {
        return cashReceive;
    }

    public void setCashReceive(double cashReceive) {
        this.cashReceive = cashReceive;
    }

    public double getNetAmt() {
        return netAmt;
    }

    public void setNetAmt(double netAmt) {
        this.netAmt = netAmt;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(int returnCount) {
        this.returnCount = returnCount;
    }

    public int getCashReceiveCount() {
        return cashReceiveCount;
    }

    public void setCashReceiveCount(int cashReceiveCount) {
        this.cashReceiveCount = cashReceiveCount;
    }

    public int getNotVisitCount() {
        return notVisitCount;
    }

    public void setNotVisitCount(int notVisitCount) {
        this.notVisitCount = notVisitCount;
    }
}
