package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 2/15/17.
 */

public class PreOrderRequestData {
    /**
     * preorder
     */
    @SerializedName("preorder")
    @Expose
    private List<PreOrderApi> data = null;

    @SerializedName("preorder_present")
    @Expose
    private List<PreOrderPresentApi> preorderPresent = null;

    public List<PreOrderApi> getData() {
        return data;
    }

    public void setData(List<PreOrderApi> data) {
        this.data = data;
    }

    public List<PreOrderPresentApi> getPreorderPresent() {
        return preorderPresent;
    }

    public void setPreorderPresent(List<PreOrderPresentApi> preorderPresent) {
        this.preorderPresent = preorderPresent;
    }
}
