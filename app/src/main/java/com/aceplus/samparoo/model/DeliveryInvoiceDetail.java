package com.aceplus.samparoo.model;

/**
 * Created by phonelin on 3/9/17.
 */

public class DeliveryInvoiceDetail {

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    String product_Name,qty;

}
