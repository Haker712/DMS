package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aceplus_mobileteam on 7/7/17.
 */

public class IncentiveUpload {

    @SerializedName("site_activation_key")
    @Expose
    private String siteActivationKey;
    @SerializedName("tablet_activation_key")
    @Expose
    private String tabletActivationKey;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("route")
    @Expose
    private String route;
    @SerializedName("data")
    @Expose
    List<IncentiveUploadData> incentiveUploadList;

    public String getSiteActivationKey() {
        return siteActivationKey;
    }

    public void setSiteActivationKey(String siteActivationKey) {
        this.siteActivationKey = siteActivationKey;
    }

    public String getTabletActivationKey() {
        return tabletActivationKey;
    }

    public void setTabletActivationKey(String tabletActivationKey) {
        this.tabletActivationKey = tabletActivationKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public List<IncentiveUploadData> getIncentiveUploadList() {
        return incentiveUploadList;
    }

    public void setIncentiveUploadList(List<IncentiveUploadData> incentiveUploadList) {
        this.incentiveUploadList = incentiveUploadList;
    }
}
