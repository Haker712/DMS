package com.aceplus.samparoo.model;

/**
 * Created by yma on 2/20/17.
 *
 * ShopType
 */

public class ShopType {

    /**
     * id
     */
    private String id;

    /**
     * shopTypeNo
     */
    private String shopTypeNo;

    /**
     * shopTypeName
     */
    private String shopTypeName;

    /**
     * Getter method for id
     *
     * @return id shop_type_id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for id
     *
     * @param id shop_type_id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for shopTypeNo
     *
     * @return shopTypeNo
     */
    public String getShopTypeNo() {
        return shopTypeNo;
    }

    /**
     * Setter method for shopTypeNo
     *
     * @param shopTypeNo shop_type_no
     */
    public void setShopTypeNo(String shopTypeNo) {
        this.shopTypeNo = shopTypeNo;
    }

    /**
     * Getter method for shopTypeName
     *
     * @return shopTypeName
     */
    public String getShopTypeName() {
        return shopTypeName;
    }

    /**
     * Setter method for shopTypeName
     *
     * @param shopTypeName shop_type_name
     */
    public void setShopTypeName(String shopTypeName) {
        this.shopTypeName = shopTypeName;
    }
}
