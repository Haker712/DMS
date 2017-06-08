package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by haker on 2/3/17.
 */
public class LoginResponse {

    @SerializedName("aceplusStatusCode")
    @Expose
    private Integer aceplusStatusCode;
    @SerializedName("aceplusStatusMessage")
    @Expose
    private String aceplusStatusMessage;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("route")
    @Expose
    private Integer route;
    @SerializedName("tablet_id")
    @Expose
    private Integer tabletKey;
    @SerializedName("max_id")
    @Expose
    private Integer maxKey;
    @SerializedName("data")
    @Expose
    private List<DataForLogin> dataForLogin = null;

    public Integer getAceplusStatusCode() {
        return aceplusStatusCode;
    }

    public void setAceplusStatusCode(Integer aceplusStatusCode) {
        this.aceplusStatusCode = aceplusStatusCode;
    }

    public String getAceplusStatusMessage() {
        return aceplusStatusMessage;
    }

    public void setAceplusStatusMessage(String aceplusStatusMessage) {
        this.aceplusStatusMessage = aceplusStatusMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRoute() {
        return route;
    }

    public void setRoute(Integer route) {
        this.route = route;
    }

    public Integer getTabletKey() {
        return tabletKey;
    }

    public void setTabletKey(Integer tabletKey) {
        this.tabletKey = tabletKey;
    }

    public Integer getMaxKey() {
        return maxKey;
    }

    public void setMaxKey(Integer maxKey) {
        this.maxKey = maxKey;
    }

    public List<DataForLogin> getDataForLogin() {
        return dataForLogin;
    }

    public void setDataForLogin(List<DataForLogin> dataForLogin) {
        this.dataForLogin = dataForLogin;
    }
}
