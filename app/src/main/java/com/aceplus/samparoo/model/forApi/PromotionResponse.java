package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 2/10/17.
 */

public class PromotionResponse {

    @SerializedName("aceplusStatusCode")
    @Expose
    private Integer aceplusStatusCode;

    @SerializedName("aceplusStatusMessage")
    @Expose
    private String aceplusStatusMessage;

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("data")
    @Expose
    private List<PromotionForApi> promotionForApi;

    public List<PromotionForApi> getPromotionForApi() {
        return promotionForApi;
    }

    public void setPromotionForApi(List<PromotionForApi> promotionForApi) {
        this.promotionForApi = promotionForApi;
    }

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
}
