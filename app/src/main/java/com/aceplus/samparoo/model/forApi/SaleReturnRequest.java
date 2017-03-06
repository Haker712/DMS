package com.aceplus.samparoo.model.forApi;

import com.aceplus.samparoo.model.SaleReturn;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 2/16/17.
 *
 * SaleReturnRequest
 */

public class SaleReturnRequest {
    /**
     * siteActivationKey
     */
    @SerializedName("site_activation_key")
    @Expose
    private String siteActivationKey;

    /**
     * tabletActivationKey
     */
    @SerializedName("tablet_activation_key")
    @Expose
    private String tabletActivationKey;

    /**
     * userId
     */
    @SerializedName("user_id")
    @Expose
    private String userId;

    /**
     * password
     */
    @SerializedName("password")
    @Expose
    private String password;

    /**
     * sale man id
     */
    @SerializedName("saleman_id")
    @Expose
    private int salemanId;

    /**
     * route
     */
    @SerializedName("route")
    @Expose
    private String route;

    /**
     * data
     */
    @SerializedName("data")
    @Expose
    private List<SaleReturnRequestData> data = null;

    /**
     * Getter method of siteActivationKey
     *
     * @return siteActivationKey
     */
    public String getSiteActivationKey() {
        return siteActivationKey;
    }

    /**
     * Setter method of siteActivationKey
     *
     * @param siteActivationKey site activation key
     */
    public void setSiteActivationKey(String siteActivationKey) {
        this.siteActivationKey = siteActivationKey;
    }

    /**
     * Getter method of tabletActivationKey
     *
     * @return tabletActivationKey
     */
    public String getTabletActivationKey() {
        return tabletActivationKey;
    }

    /**
     * Setter method of tabletActivationKey
     *
     * @param tabletActivationKey tablet activation key
     */
    public void setTabletActivationKey(String tabletActivationKey) {
        this.tabletActivationKey = tabletActivationKey;
    }

    /**
     * Getter method of userId
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method of userId
     *
     * @param userId user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method of password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method of password
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter method of salemanId
     *
     * @return salemanId
     */
    public int getSalemanId() {
        return salemanId;
    }

    /**
     * Setter method of salemanId
     *
     * @param salemanId sale man id
     */
    public void setSalemanId(int salemanId) {
        this.salemanId = salemanId;
    }

    /**
     * Getter method of route
     *
     * @return route
     */
    public String getRoute() {
        return route;
    }

    /**
     * Setter method of route
     *
     * @param route route
     */
    public void setRoute(String route) {
        this.route = route;
    }

    /**
     * Getter method of sale return list
     *
     * @return data
     */
    public List<SaleReturnRequestData> getData() {
        return data;
    }

    /**
     * Setter method of sale return list
     *
     * @param data sale return list
     */
    public void setData(List<SaleReturnRequestData> data) {
        this.data = data;
    }
}
