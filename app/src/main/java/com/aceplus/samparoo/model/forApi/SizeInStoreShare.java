package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 3/21/17.
 */

public class SizeInStoreShare {

    @SerializedName("SizeInStoreShareNo")
    @Expose
    private String sizeInStoreShareNo;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("StockId")
    @Expose
    private Integer stockId;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Remark")
    @Expose
    private String remark;
    @SerializedName("SaleManId")
    @Expose
    private Integer salemanId;

    private String customerNo;

    private String customerName;

    public String getSizeInStoreShareNo() {
        return sizeInStoreShareNo;
    }

    public void setSizeInStoreShareNo(String sizeInStoreShareNo) {
        this.sizeInStoreShareNo = sizeInStoreShareNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSalemanId() {
        return salemanId;
    }

    public void setSalemanId(Integer salemanId) {
        this.salemanId = salemanId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
