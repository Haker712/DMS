package com.aceplus.samparoo.model;

import java.io.Serializable;

/**
 * Created by haker on 2/13/17.
 */

public class Promotion implements Serializable {

    String promotionPlanId;

    Double promotionPrice;

    String promotionProductId;

    String promotionProductName;

    int promotionQty;

    Double price;

    int currencyId;

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

    public String getPromotionPlanId() {
        return promotionPlanId;
    }

    public void setPromotionPlanId(String promotionPlanId) {
        this.promotionPlanId = promotionPlanId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }
}
