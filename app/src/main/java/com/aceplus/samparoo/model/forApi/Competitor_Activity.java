package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phonelin on 4/4/17.
 */

public class Competitor_Activity {

    @SerializedName("CompetitorActivitiesNo")
    @Expose
    private String competitorActivitiesNo;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("CompetitorName")
    @Expose
    private String competitorName;
    @SerializedName("Activities")
    @Expose
    private String activities;
    @SerializedName("Remark")
    @Expose
    private String remark;
    @SerializedName("SaleManId")
    @Expose
    private Integer saleManId;
    @SerializedName("InvoiceDate")
    @Expose
    private String invoiceDate;

    public String getCompetitorActivitiesNo() {
        return competitorActivitiesNo;
    }

    public void setCompetitorActivitiesNo(String competitorActivitiesNo) {
        this.competitorActivitiesNo = competitorActivitiesNo;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCompetitorName() {
        return competitorName;
    }

    public void setCompetitorName(String competitorName) {
        this.competitorName = competitorName;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(Integer saleManId) {
        this.saleManId = saleManId;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}
