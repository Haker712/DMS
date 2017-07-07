package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aceplus_mobileteam on 7/7/17.
 */

public class IncentivePaidUploadData {

    @SerializedName("InvoiceNo")
    @Expose
    String invoiceNo;

    @SerializedName("InvoiceDate")
    @Expose
    String invoiceDate;

    @SerializedName("CustomerID")
    @Expose
    Integer customerId;

    @SerializedName("StockId")
    @Expose
    Integer stockId;

    @SerializedName("Quantity")
    @Expose
    Integer quantity;

    @SerializedName("Paid_Quantity")
    @Expose
    Integer paidQuantity;

    @SerializedName("SaleManID")
    @Expose
    Integer saleManId;

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
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

    public Integer getPaidQuantity() {
        return paidQuantity;
    }

    public void setPaidQuantity(Integer paidQuantity) {
        this.paidQuantity = paidQuantity;
    }

    public Integer getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(Integer saleManId) {
        this.saleManId = saleManId;
    }
}
