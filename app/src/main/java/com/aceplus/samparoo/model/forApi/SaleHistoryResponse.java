package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 4/11/17.
 *
 * Holder for Sale History that are downloaded from api
 */

public class SaleHistoryResponse {

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
    private List<DataForSaleHistory> dataForSaleHistoryList = null;

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
     * @param aceplusStatusCode
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
     * @param aceplusStatusMessage
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
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for dataForSaleHistoryList
     *
     * @return dataForSaleHistoryList
     */
    public List<DataForSaleHistory> getDataForSaleHistoryList() {
        return dataForSaleHistoryList;
    }

    /**
     * Setter method for dataForSaleHistoryList
     *
     * @param dataForSaleHistoryList
     */
    public void setDataForSaleHistoryList(List<DataForSaleHistory> dataForSaleHistoryList) {
        this.dataForSaleHistoryList = dataForSaleHistoryList;
    }
}
