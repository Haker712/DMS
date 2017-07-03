package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aceplus_mobileteam on 6/29/17.
 */

public class DataForIncentive {
    @SerializedName("DisplayProgram")
    @Expose
    private List<IncentiveForApi> IncentiveList = null;

    public List<IncentiveForApi> getIncentiveList() {
        return IncentiveList;
    }

    public void setIncentiveList(List<IncentiveForApi> incentiveList) {
        IncentiveList = incentiveList;
    }
}
