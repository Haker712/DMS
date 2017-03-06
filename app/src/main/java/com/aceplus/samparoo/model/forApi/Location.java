package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by haker on 2/15/17.
 */
public class Location {
    @SerializedName("LocationId")
    @Expose
    private String locationId;
    @SerializedName("LocationNo")
    @Expose
    private String locationNo;
    @SerializedName("LocationName")
    @Expose
    private String locationName;
    @SerializedName("BranchId")
    @Expose
    private String branchId;
    @SerializedName("BranchNo")
    @Expose
    private String branchNo;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationNo() {
        return locationNo;
    }

    public void setLocationNo(String locationNo) {
        this.locationNo = locationNo;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }
}
