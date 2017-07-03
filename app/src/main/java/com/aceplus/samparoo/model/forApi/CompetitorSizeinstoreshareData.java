package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 4/4/17.
 */

public class CompetitorSizeinstoreshareData {

    @SerializedName("SizeAndStockShare")
    @Expose
    private List<SizeInStoreShare> sizeInStoreShare = null;

    public List<SizeInStoreShare> getSizeInStoreShare() {
        return sizeInStoreShare;
    }

    public void setSizeInStoreShare(List<SizeInStoreShare> sizeInStoreShare) {
        this.sizeInStoreShare = sizeInStoreShare;
    }


}
