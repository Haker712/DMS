package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yma on 4/11/17.
 *
 * SaleHistoryDetail
 */

public class SaleHistoryDetail {

    /**
     * tsale_id
     */
    @SerializedName("tsale_id")
    @Expose
    String tSaleId;

    /**
     * product_id
     */
    @SerializedName("product_id")
    @Expose
    String productId;

    /**
     * qty
     */
    @SerializedName("qty")
    @Expose
    String qty;

    /**
     * discount_amt
     */
    @SerializedName("discount_amt")
    @Expose
    String discountAmt;

    /**
     * Getter method for tSaleId
     *
     * @return tSaleId
     */
    public String gettSaleId() {
        return tSaleId;
    }

    /**
     * Setter method for sale id
     *
     * @param tSaleId
     */
    public void settSaleId(String tSaleId) {
        this.tSaleId = tSaleId;
    }

    /**
     * Getter method for productId
     *
     * @return productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Setter method for product id
     *
     * @param productId productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Getter method for quantity
     *
     * @return qty
     */
    public String getQty() {
        return qty;
    }

    /**
     * Setter method for quantity
     *
     * @param qty quantity
     */
    public void setQty(String qty) {
        this.qty = qty;
    }

    /**
     * Getter method for discountAmt
     *
     * @return discountAmt
     */
    public String getDiscountAmt() {
        return discountAmt;
    }

    /**
     * Setter method for discount amount
     *
     * @param discountAmt discount amount
     */
    public void setDiscountAmt(String discountAmt) {
        this.discountAmt = discountAmt;
    }
}
