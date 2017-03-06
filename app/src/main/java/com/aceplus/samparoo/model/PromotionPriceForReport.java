package com.aceplus.samparoo.model;

/**
 * Created by haker on 2/23/17.
 */
public class PromotionPriceForReport {

    String date, product_id, product_name, from_quantity, to_quantity, promotion_price;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getFrom_quantity() {
        return from_quantity;
    }

    public void setFrom_quantity(String from_quantity) {
        this.from_quantity = from_quantity;
    }

    public String getTo_quantity() {
        return to_quantity;
    }

    public void setTo_quantity(String to_quantity) {
        this.to_quantity = to_quantity;
    }

    public String getPromotion_price() {
        return promotion_price;
    }

    public void setPromotion_price(String promotion_price) {
        this.promotion_price = promotion_price;
    }
}
