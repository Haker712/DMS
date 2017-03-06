package com.aceplus.samparoo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by haker on 2/3/17.
 */
public class LoginResponse {

    @SerializedName("aceplusStatusCode")
    public String aceplusStatusCode;
    @SerializedName("aceplusStatusMessage")
    public String aceplusStatusMessage;

    public String getAceplusStatusCode() {
        return aceplusStatusCode;
    }

    public void setAceplusStatusCode(String aceplusStatusCode) {
        this.aceplusStatusCode = aceplusStatusCode;
    }

    public String getAceplusStatusMessage() {
        return aceplusStatusMessage;
    }

    public void setAceplusStatusMessage(String aceplusStatusMessage) {
        this.aceplusStatusMessage = aceplusStatusMessage;
    }
}
