package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aceplus_mobileteam on 2/20/17.
 */

public class PosmShopTypeForApi {

    /**
     * POSM List
     */
    @SerializedName("POSM")
    @Expose
    private List<PosmForApi> posmForApiList = null;

    /**
     * SHOP TYPE List
     */
    @SerializedName("ShopType")
    @Expose
    private List<ShopTypeForApi> shopTypeForApiList = null;

    /**
     * Getter method for posmApi list
     *
     * @return posmForApiList
     */
    public List<PosmForApi> getPosmForApiList() {
        return posmForApiList;
    }

    /**
     * Setter method for posmApi list
     * @param posmForApiList list of posmApi
     */
    public void setPosmForApiList(List<PosmForApi> posmForApiList) {
        this.posmForApiList = posmForApiList;
    }

    /**
     * Getter method for shop type api list
     *
     * @return shopTypeForApiList
     */
    public List<ShopTypeForApi> getShopTypeForApiList() {
        return shopTypeForApiList;
    }

    /**
     * Setter method for ShopTypeForApi
     *
     * @param shopTypeForApiList ShopTypeForApi list
     */
    public void setShopTypeForApiList(List<ShopTypeForApi> shopTypeForApiList) {
        this.shopTypeForApiList = shopTypeForApiList;
    }
}
