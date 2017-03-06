package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 2/21/17.
 *
 * PosmByCustomerRequest
 */

public class PosmByCustomerRequest {

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
    private List<PosmByCustomerRequestData> data = null;

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
     * Getter method of PosmByCustomerRequestData list
     *
     * @return data
     */
    public List<PosmByCustomerRequestData> getData() {
        return data;
    }

    /**
     * Setter method of PosmByCustomerRequestData list
     *
     * @param data PosmByCustomerRequestData list
     */
    public void setData(List<PosmByCustomerRequestData> data) {
        this.data = data;
    }
}
