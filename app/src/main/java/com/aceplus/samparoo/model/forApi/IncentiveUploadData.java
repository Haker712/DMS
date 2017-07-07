package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aceplus_mobileteam on 7/7/17.
 */

public class IncentiveUploadData {

    @SerializedName("Incentive_Paid")
    @Expose
    List<IncentivePaidUploadData> incentivePaidUploadDataList;

    public List<IncentivePaidUploadData> getIncentivePaidUploadDataList() {
        return incentivePaidUploadDataList;
    }

    public void setIncentivePaidUploadDataList(List<IncentivePaidUploadData> incentivePaidUploadDataList) {
        this.incentivePaidUploadDataList = incentivePaidUploadDataList;
    }
}
