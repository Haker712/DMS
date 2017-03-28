package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 3/21/17.
 */

public class Outlet_Sizeinstore_Data {
    @SerializedName("OutletStockAvailability")
    @Expose
    private List<OutletStockAvailability> outletStockAvailability = null;
    @SerializedName("SizeInStoreShare")
    @Expose
    private List<SizeInStoreShare> sizeInStoreShare = null;

    public List<OutletStockAvailability> getOutletStockAvailability() {
        return outletStockAvailability;
    }

    public void setOutletStockAvailability(List<OutletStockAvailability> outletStockAvailability) {
        this.outletStockAvailability = outletStockAvailability;
    }

    public List<SizeInStoreShare> getSizeInStoreShare() {
        return sizeInStoreShare;
    }

    public void setSizeInStoreShare(List<SizeInStoreShare> sizeInStoreShare) {
        this.sizeInStoreShare = sizeInStoreShare;
    }


}
