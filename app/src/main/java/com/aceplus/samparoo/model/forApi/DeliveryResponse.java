package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 2/22/17.
 *
 * DeliveryResponse
 */

public class DeliveryResponse {

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
     * dataForDeliveryList
     */
    @SerializedName("data")
    @Expose
    private List<DataForDelivery> dataForDeliveryList = null;

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
     * @param aceplusStatusCode integer
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
     * @param aceplusStatusMessage String
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
     * @param userId userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for dataForDeliveryList
     *
     * @return dataForDeliveryList
     */
    public List<DataForDelivery> getDataForDeliveryList() {
        return dataForDeliveryList;
    }

    /**
     * Setter method for dataForDeliveryList
     *
     * @param dataForDeliveryList receive object for delivery from api
     */
    public void setDataForDeliveryList(List<DataForDelivery> dataForDeliveryList) {
        this.dataForDeliveryList = dataForDeliveryList;
    }
}
