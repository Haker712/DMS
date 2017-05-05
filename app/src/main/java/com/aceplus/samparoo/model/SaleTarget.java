package com.aceplus.samparoo.model;

/**
 * Created by yma on 4/5/17.
 *
 * SaleTarget
 */
public class SaleTarget {
    /**
     * productId
     */
    String productId = null;

    /**
     * productName
     */
    String productName = null;

    /**
     * quantity
     */
    double quantity = 0;

    /**
     * sellingPrice
     */
    double sellingPrice = 0;

    /**
     * totalAmount
     */
    double totalAmount = 0;

    /**
     * categoryId
     */
    int categoryId = 0;

    /**
     * groupCodeId
     */
    int groupCodeId = 0;

    /**
     * customerId
     */
    int customerId = 0;

    /**
     * Getter method for productId
     *
     * @return productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Setter method for productId
     *
     * @param productId productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Getter method for productName
     *
     * @return productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Setter method for productName
     *
     * @param productName productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Getter method for quantity
     *
     * @return quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Setter method for quantity
     *
     * @param quantity quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter method for sellingPrice
     *
     * @return sellingPrice
     */
    public double getSellingPrice() {
        return sellingPrice;
    }

    /**
     * Setter method for sellingPrice
     *
     * @param sellingPrice sellingPrice
     */
    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    /**
     * Getter method for totalAmount
     *
     * @return totalAmount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Setter method for totalAmount
     *
     * @param totalAmount totalAmount
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Getter method for categoryId
     *
     * @return categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Setter method for categoryId
     *
     * @param categoryId categoryId
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Getter method for groupCodeId
     *
     * @return groupCodeId
     */
    public int getGroupCodeId() {
        return groupCodeId;
    }

    /**
     * Setter method for groupCodeId
     *
     * @param groupCodeId groupCodeId
     */
    public void setGroupCodeId(int groupCodeId) {
        this.groupCodeId = groupCodeId;
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
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}

