package com.aceplus.samparoo.model.forApi;

/**
 * Created by yma on 2/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Call to API by Retrofit
 */
public class PromotionRequest {

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
     * date
     */
    @SerializedName("date")
    @Expose
    private String date;

    /**
     * data
     */
    @SerializedName("data")
    @Expose
    private List<Object> data = null;

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
     * @param siteActivationKey String
     */
    public void setSiteActivationKey(String siteActivationKey) {
        this.siteActivationKey = siteActivationKey;
    }

    /**
     * Getter method for tabletActivationKey
     *
     * @return tabletActivationKey
     */
    public String getTabletActivationKey() {
        return tabletActivationKey;
    }

    /**
     * Setter method for tabletActivationKey
     *
     * @param tabletActivationKey
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
     * @param userId String
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
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter method for date
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter method for date
     *
     * @param date String
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter method for data
     *
     * @return data
     */
    public List<Object> getData() {
        return data;
    }

    /**
     * Setter method for data
     *
     * @param data Object list
     */
    public void setData(List<Object> data) {
        this.data = data;
    }
}
