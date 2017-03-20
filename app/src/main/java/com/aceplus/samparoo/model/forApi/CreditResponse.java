package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 3/15/17.
 *
 * Download credit data from API.
 */

public class CreditResponse {

    /**
     * aceplusStatusCode
     */
    @SerializedName("aceplusStatusCode")
    @Expose
    private Integer aceplusStatusCode;

    /**
     * aceplusStatusMessage
     */
    @SerializedName("aceplusStatusMessage")
    @Expose
    private String aceplusStatusMessage;

    /**
     * userId
     */
    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("data")
    @Expose
    private List<DataForCredit> dataForCreditList = null;

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

    public List<DataForCredit> getDataForCreditList() {
        return dataForCreditList;
    }

    public void setDataForCreditList(List<DataForCredit> dataForCreditList) {
        this.dataForCreditList = dataForCreditList;
    }
}
