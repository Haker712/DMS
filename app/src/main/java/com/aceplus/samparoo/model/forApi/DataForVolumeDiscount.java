package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by haker on 2/10/17.
 */
public class DataForVolumeDiscount {
    @SerializedName("VolumeDiscount")
    @Expose
    private List<VolumeDiscount> volumeDiscount = null;
    @SerializedName("VolumeDiscountFilter")
    @Expose
    private List<VolumeDiscountFilter> volumeDiscountFilter = null;

    public List<VolumeDiscount> getVolumeDiscount() {
        return volumeDiscount;
    }

    public void setVolumeDiscount(List<VolumeDiscount> volumeDiscount) {
        this.volumeDiscount = volumeDiscount;
    }

    public List<VolumeDiscountFilter> getVolumeDiscountFilter() {
        return volumeDiscountFilter;
    }

    public void setVolumeDiscountFilter(List<VolumeDiscountFilter> volumeDiscountFilter) {
        this.volumeDiscountFilter = volumeDiscountFilter;
    }
}
