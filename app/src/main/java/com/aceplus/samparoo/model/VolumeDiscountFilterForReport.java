package com.aceplus.samparoo.model;

import java.util.List;

/**
 * Created by yma on 5/11/17.
 */

public class VolumeDiscountFilterForReport {

    int volumeDiscountId;

    String discountPlanNo;

    String fromDate;

    String toDate;

    String filterExclude;

    List<VolumeDiscountFilterItemForReport> volumeDiscountFilterItemForReportList;

    public int getVolumeDiscountId() {
        return volumeDiscountId;
    }

    public void setVolumeDiscountId(int volumeDiscountId) {
        this.volumeDiscountId = volumeDiscountId;
    }

    public String getDiscountPlanNo() {
        return discountPlanNo;
    }

    public void setDiscountPlanNo(String discountPlanNo) {
        this.discountPlanNo = discountPlanNo;
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

    public String getFilterExclude() {
        return filterExclude;
    }

    public void setFilterExclude(String filterExclude) {
        this.filterExclude = filterExclude;
    }

    public List<VolumeDiscountFilterItemForReport> getVolumeDiscountFilterItemForReportList() {
        return volumeDiscountFilterItemForReportList;
    }

    public void setVolumeDiscountFilterItemForReportList(List<VolumeDiscountFilterItemForReport> volumeDiscountFilterItemForReportList) {
        this.volumeDiscountFilterItemForReportList = volumeDiscountFilterItemForReportList;
    }
}
