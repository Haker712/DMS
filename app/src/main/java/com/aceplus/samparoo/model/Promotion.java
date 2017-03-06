package com.aceplus.samparoo.model;

/**
 * Created by haker on 2/13/17.
 */

public class Promotion {
    Double promotionPrice;

    String promotionProductId;

    String promotionProductName;

    int promotionQty;

    public Double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getPromotionProductId() {
        return promotionProductId;
    }

    public void setPromotionProductId(String promotionProductId) {
        this.promotionProductId = promotionProductId;
    }

    public String getPromotionProductName() {
        return promotionProductName;
    }

    public void setPromotionProductName(String promotionProductName) {
        this.promotionProductName = promotionProductName;
    }

    public int getPromotionQty() {
        return promotionQty;
    }

    public void setPromotionQty(int promotionQty) {
        this.promotionQty = promotionQty;
    }
}
