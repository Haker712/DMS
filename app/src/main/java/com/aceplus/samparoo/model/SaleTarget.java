package com.aceplus.samparoo.model;

/**
 * Created by aceplus_mobileteam on 4/5/17.
 */

public class SaleTarget {
    String productId = null;
    String productName = null;
    int quantity = 0;
    double sellingPrice = 0;
    double totalAmount = 0;
    int categoryId = 0;
    int groupCodeId = 0;
    int customerId = 0;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getGroupCodeId() {
        return groupCodeId;
    }

    public void setGroupCodeId(int groupCodeId) {
        this.groupCodeId = groupCodeId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}

