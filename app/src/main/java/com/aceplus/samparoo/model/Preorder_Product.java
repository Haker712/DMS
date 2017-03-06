package com.aceplus.samparoo.model;

/**
 * Created by phonelin on 3/1/17.
 */

public class Preorder_Product {


    String productName;
    String orderQty;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(String orderQty) {
        this.orderQty = orderQty;
    }

    String totalAmount;


}
