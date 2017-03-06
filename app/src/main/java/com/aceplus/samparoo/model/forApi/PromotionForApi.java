package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 2/10/17.
 * <p>
 * Model for response data.
 */

public class PromotionForApi {

    /**
     * PromotionDate List from Json
     */
    @SerializedName("PromotionDate")
    @Expose
    List<PromotionDate> promotionDateList;

    /**
     * PromotionPrice List from Json
     */
    @SerializedName("PromotionPrice")
    @Expose
    List<PromotionPrice> promotionPriceList;

    /**
     * PromotionGift List from Json
     */
    @SerializedName("PromotionGift")
    @Expose
    List<PromotionGift> promotionGiftList;

    /**
     * PromotionGiftItem List from Json
     */
    @SerializedName("PromotionGiftItem")
    @Expose
    List<PromotionGiftItem> promotionGiftItemList;

    /**
     * Getter method for promotionDate
     *
     * @return promotionDateList
     */
    public List<PromotionDate> getPromotionDateList() {
        return promotionDateList;
    }

    /**
     * Setter method for promotionDate
     *
     * @param promotionDateList
     */
    public void setPromotionDateList(List<PromotionDate> promotionDateList) {
        this.promotionDateList = promotionDateList;
    }

    /**
     * Getter method for promotionPrice
     *
     * @return promotionPriceList
     */
    public List<PromotionPrice> getPromotionPriceList() {
        return promotionPriceList;
    }

    /**
     * Setter method for promotionPrice
     *
     * @param promotionPriceList
     */
    public void setPromotionPriceList(List<PromotionPrice> promotionPriceList) {
        this.promotionPriceList = promotionPriceList;
    }

    /**
     * Getter method for promotionGift
     *
     * @return promotionGiftList
     */
    public List<PromotionGift> getPromotionGiftList() {
        return promotionGiftList;
    }

    /**
     * Setter method for promotionGift
     *
     * @param promotionGiftList
     */
    public void setPromotionGiftList(List<PromotionGift> promotionGiftList) {
        this.promotionGiftList = promotionGiftList;
    }

    /**
     * Getter method for promotionGiftItem
     *
     * @return promotionGiftItemList
     */
    public List<PromotionGiftItem> getPromotionGiftItemList() {
        return promotionGiftItemList;
    }

    /**
     * Setter method for promotionGiftItem
     *
     * @param promotionGiftItemList
     */
    public void setPromotionGiftItemList(List<PromotionGiftItem> promotionGiftItemList) {
        this.promotionGiftItemList = promotionGiftItemList;
    }
}
