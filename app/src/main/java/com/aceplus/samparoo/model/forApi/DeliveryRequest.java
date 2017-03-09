package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 3/8/17.
 *
 * DeliveryRequest
 */

public class DeliveryRequest {

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
     * data
     */
    @SerializedName("data")
    @Expose
    private List<DeliveryRequestData> data = null;

    /**
     * Getter method for siteActivationKey
     *
     * @return siteActivationKey
     */
    public String getSiteActivationKey() {
        return siteActivationKey;
    }

    /**
     * Setter method for siteActivationKey
     *
     * @param siteActivationKey site activation key
     */
    public void setSiteActivationKey(String siteActivationKey) {
        this.siteActivationKey = siteActivationKey;
    }

    /**
     * Getter method for tablet activation key
     *
     * @return tabletActivationKey
     */
    public String getTabletActivationKey() {
        return tabletActivationKey;
    }

    /**
     * Setter method for tablet activation key
     *
     * @param tabletActivationKey tablet activation key
     */
    public void setTabletActivationKey(String tabletActivationKey) {
        this.tabletActivationKey = tabletActivationKey;
    }

    /**
     * Getter method for userId
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for userId
     *
     * @param userId userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for password
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter method for data
     *
     * @return data
     */
    public List<DeliveryRequestData> getData() {
        return data;
    }

    /**
     * Setter method for data
     *
     * @param data data
     */
    public void setData(List<DeliveryRequestData> data) {
        this.data = data;
    }
}
