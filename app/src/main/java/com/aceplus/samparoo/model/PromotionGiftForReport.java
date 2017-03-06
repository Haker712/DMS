package com.aceplus.samparoo.model;

/**
 * Created by yma on 2/24/17.
 *
 * PromotionGiftForReport
 */

public class PromotionGiftForReport {

    /**
     * promotionPlanId
     */
    private String promotionPlanId;

    /**
     * stockId
     */
    private String stockId;

    /**
     * fromQuantity
     */
    private int fromQuantity;

    /**
     * toQuantity
     */
    private int toQuantity;

    /**
     * product name
     */
    private String productName;

    /**
     * promotionGiftName
     */
    private String promotionGiftName;

    /**
     * promotionGiftQuantity
     */
    private int promotionGiftQuantity;

    /**
     * Getter method for promotionPlanId
     *
     * @return promotionPlanId
     */
    public String getPromotionPlanId() {
        return promotionPlanId;
    }

    /**
     * Setter method for promotionPlanId
     *
     * @param promotionPlanId promotion plan id
     */
    public void setPromotionPlanId(String promotionPlanId) {
        this.promotionPlanId = promotionPlanId;
    }

    /**
     * Getter method for stockId
     *
     * @return stockId
     */
    public String getStockId() {
        return stockId;
    }

    /**
     * Setter method for stockId
     *
     * @param stockId stock id (product id)
     */
    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    /**
     * Getter method for fromQuantity
     *
     * @return fromQuantity
     */
    public int getFromQuantity() {
        return fromQuantity;
    }

    /**
     * Setter method for
     *
     * @param fromQuantity gift quantity(From)
     */
    public void setFromQuantity(int fromQuantity) {
        this.fromQuantity = fromQuantity;
    }

    /**
     * Getter method for
     *
     * @return
     */
    public int getToQuantity() {
        return toQuantity;
    }

    /**
     * Setter method for toQuantity
     *
     * @param toQuantity gift quantity(To)
     */
    public void setToQuantity(int toQuantity) {
        this.toQuantity = toQuantity;
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
     * Setter method for producName
     *
     * @param productName product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Getter method for promotionGiftName
     *
     * @return promotionGiftName
     */
    public String getPromotionGiftName() {
        return promotionGiftName;
    }

    /**
     * Setter method for promotionGiftName
     *
     * @param promotionGiftName promotion gift product name
     */
    public void setPromotionGiftName(String promotionGiftName) {
        this.promotionGiftName = promotionGiftName;
    }

    /**
     * Getter method for promotion gift quantity
     *
     * @return promotionGiftQuantity
     */
    public int getPromotionGiftQuantity() {
        return promotionGiftQuantity;
    }

    /**
     * Setter method for promotionGiftQuantity
     *
     * @param promotionGiftQuantity product name
     */
    public void setPromotionGiftQuantity(int promotionGiftQuantity) {
        this.promotionGiftQuantity = promotionGiftQuantity;
    }
}
