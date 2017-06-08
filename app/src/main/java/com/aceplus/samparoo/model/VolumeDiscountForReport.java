package com.aceplus.samparoo.model;

import java.util.List;

/**
 * Created by yma on 5/11/17.
 */

public class VolumeDiscountForReport {

    int volumeDiscountId;

    String volumeDiscountPlanNo;

    String fromDate;

    String toDate;

    String volumeDiscountExclude;

    List<VolumeDiscountItemForReport> volumeDiscountItemForReport;

    public int getVolumeDiscountId() {
        return volumeDiscountId;
    }

    public void setVolumeDiscountId(int volumeDiscountId) {
        this.volumeDiscountId = volumeDiscountId;
    }

    public String getVolumeDiscountPlanNo() {
        return volumeDiscountPlanNo;
    }

    public void setVolumeDiscountPlanNo(String volumeDiscountPlanNo) {
        this.volumeDiscountPlanNo = volumeDiscountPlanNo;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getVolumeDiscountExclude() {
        return volumeDiscountExclude;
    }

    public void setVolumeDiscountExclude(String volumeDiscountExclude) {
        this.volumeDiscountExclude = volumeDiscountExclude;
    }

    public List<VolumeDiscountItemForReport> getVolumeDiscountItemForReport() {
        return volumeDiscountItemForReport;
    }

    public void setVolumeDiscountItemForReport(List<VolumeDiscountItemForReport> volumeDiscountItemForReport) {
        this.volumeDiscountItemForReport = volumeDiscountItemForReport;
    }
}
