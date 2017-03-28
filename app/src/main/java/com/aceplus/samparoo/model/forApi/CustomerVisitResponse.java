package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 3/28/17.
 *
 * CustomerVisitResponse
 */

public class CustomerVisitResponse {

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

    /**
     * customerVisitRequestDataList
     */
    @SerializedName("data")
    @Expose
    private List<CustomerVisitRequestData> customerVisitRequestDataList = null;

    /**
     * Getter method for aceplusStatusCode
     *
     * @return aceplusStatusCode
     */
    public Integer getAceplusStatusCode() {
        return aceplusStatusCode;
    }

    /**
     * Setter method for aceplusStatusCode
     *
     * @param aceplusStatusCode aceplusStatusCode
     */
    public void setAceplusStatusCode(Integer aceplusStatusCode) {
        this.aceplusStatusCode = aceplusStatusCode;
    }

    /**
     * Getter method for aceplusStatusMessage
     *
     * @return aceplusStatusMessage
     */
    public String getAceplusStatusMessage() {
        return aceplusStatusMessage;
    }

    /**
     * Setter method for aceplusStatusMessage
     *
     * @param aceplusStatusMessage message from server response
     */
    public void setAceplusStatusMessage(String aceplusStatusMessage) {
        this.aceplusStatusMessage = aceplusStatusMessage;
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
     * @param userId user Id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for customerVisitRequestDataList
     *
     * @return customerVisitRequestDataList
     */
    public List<CustomerVisitRequestData> getCustomerVisitRequestDataList() {
        return customerVisitRequestDataList;
    }

    /**
     * Setter method for customerVisitRequestDataList
     *
     * @param customerVisitRequestDataList customerVisitRequestDataList
     */
    public void setCustomerVisitRequestDataList(List<CustomerVisitRequestData> customerVisitRequestDataList) {
        this.customerVisitRequestDataList = customerVisitRequestDataList;
    }
}
