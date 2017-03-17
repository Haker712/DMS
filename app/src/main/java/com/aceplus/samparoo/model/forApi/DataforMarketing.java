package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 3/15/17.
 */

public class DataforMarketing {

    @SerializedName("StandardExternalCheck")
    @Expose
    private List<StandardExternalCheck> standardExternalCheck = null;

    public List<StandardExternalCheck> getStandardExternalCheck() {
        return standardExternalCheck;
    }

    public void setStandardExternalCheck(List<StandardExternalCheck> standardExternalCheck) {
        this.standardExternalCheck = standardExternalCheck;
    }


}
