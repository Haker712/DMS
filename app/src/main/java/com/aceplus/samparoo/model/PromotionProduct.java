package com.aceplus.samparoo.model;

import java.io.Serializable;

/**
 * Created by i'm lovin' her on 5/20/16.
 */
public class PromotionProduct implements Serializable {

    String promotion_product_id;
    String promotion_product_name;
    String promotion_qty;

    public PromotionProduct(String promotion_product_id, String promotion_product_name, String promotion_qty) {
        this.promotion_product_id = promotion_product_id;
        this.promotion_product_name = promotion_product_name;
        this.promotion_qty = promotion_qty;
    }

    public String getPromotion_product_id() {
        return promotion_product_id;
    }

    public String getPromotion_product_name() {
        return promotion_product_name;
    }

    public String getPromotion_qty() {
        return promotion_qty;
    }
}
