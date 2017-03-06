package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aceplus_mobileteam on 2/20/17.
 */

public class PosmShopTypeResponse {

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
     * data
     */
    @SerializedName("data")
    @Expose
    private List<PosmShopTypeForApi> posmShopTypeForApiList = null;

    /**
     * Getter method of aceplusStatusCode
     *
     * @return aceplusStatusCode
     */
    public Integer getAceplusStatusCode() {
        return aceplusStatusCode;
    }

    /**
     * Setter method of aceplusStatusCode
     *
     * @param aceplusStatusCode aceplus Status Code
     */
    public void setAceplusStatusCode(Integer aceplusStatusCode) {
        this.aceplusStatusCode = aceplusStatusCode;
    }

    /**
     * Getter method of aceplusStatusMessage
     *
     * @return aceplusStatusMessage
     */
    public String getAceplusStatusMessage() {
        return aceplusStatusMessage;
    }

    /**
     * Setter method of aceplusStatusMessage
     *
     * @param aceplusStatusMessage aceplusStatusMessage
     */
    public void setAceplusStatusMessage(String aceplusStatusMessage) {
        this.aceplusStatusMessage = aceplusStatusMessage;
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
     * Getter method for PosmShopTypeRequestData list
     *
     * @return data
     */
    public List<PosmShopTypeForApi> getPosmShopTypeForApiList() {
        return posmShopTypeForApiList;
    }

    /**
     * Setter method for PosmShopTypeRequestData list
     *
     * @param posmShopTypeForApiList PosmShopTypeRequestData list
     */
    public void setPosmShopTypeForApiList(List<PosmShopTypeForApi> posmShopTypeForApiList) {
        this.posmShopTypeForApiList = posmShopTypeForApiList;
    }
}
