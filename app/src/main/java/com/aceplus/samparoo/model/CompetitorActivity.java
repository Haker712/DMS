package com.aceplus.samparoo.model;

/**
 * Created by phonelin on 4/3/17.
 */

public class CompetitorActivity {

    String competitor_Name;

    String Activity;

    String id;

    Integer customerId;

    String customerName;

    String customerNo;

    Integer saleManId;

    String invoiceDate;

    public String getCompetitor_Name() {
        return competitor_Name;
    }

    public void setCompetitor_Name(String competitor_Name) {
        this.competitor_Name = competitor_Name;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
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
