package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aceplus_mobileteam on 6/29/17.
 */

public class IncentiveForApi {

    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("InvoiceNo")
    @Expose
    private String invoiceNo;

    @SerializedName("InvoiceDate")
    @Expose
    private String invoiceDate;

    @SerializedName("StandardExternalCheckId")
    @Expose
    private Integer standardExternalCheckId;

    @SerializedName("OutletVisibilityId")
    @Expose
    private Integer outletVisibilityId;

    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;

    @SerializedName("SaleManId")
    @Expose
    private Integer saleManId;

    @SerializedName("IncentiveProgramName")
    @Expose
    private String incentiveProgramName;

    @SerializedName("DisplayProgramItem")
    @Expose
    private List<IncentiveItemForApi> displayProgramItem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Integer getStandardExternalCheckId() {
        return standardExternalCheckId;
    }

    public void setStandardExternalCheckId(Integer standardExternalCheckId) {
        this.standardExternalCheckId = standardExternalCheckId;
    }

    public Integer getOutletVisibilityId() {
        return outletVisibilityId;
    }

    public void setOutletVisibilityId(Integer outletVisibilityId) {
        this.outletVisibilityId = outletVisibilityId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(Integer saleManId) {
        this.saleManId = saleManId;
    }

    public String getIncentiveProgramName() {
        return incentiveProgramName;
    }

    public void setIncentiveProgramName(String incentiveProgramName) {
        this.incentiveProgramName = incentiveProgramName;
    }

    public List<IncentiveItemForApi> getDisplayProgramItem() {
        return displayProgramItem;
    }

    public void setDisplayProgramItem(List<IncentiveItemForApi> displayProgramItem) {
        this.displayProgramItem = displayProgramItem;
    }
}
