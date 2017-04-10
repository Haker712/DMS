package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aceplus_mobileteam on 4/4/17.
 */

public class SaleTargetResponse {

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
    private List<DataForSaleTarget> dataForSaleTargetList = null;

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
     * @param aceplusStatusMessage aceplusStatusMessage
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
     * Getter method for sale target list
     *
     * @return dataForSaleTargetList
     */
    public List<DataForSaleTarget> getDataForSaleTargetList() {
        return dataForSaleTargetList;
    }

    /**
     * Setter method for dataForSaleTargetList
     *
     * @param dataForSaleTargetList sale target request
     */
    public void setDataForSaleTargetList(List<DataForSaleTarget> dataForSaleTargetList) {
        this.dataForSaleTargetList = dataForSaleTargetList;
    }
}
