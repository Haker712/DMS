package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phonelin on 4/4/17.
 */

public class CustomerFeedback {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("InvoiceNo")
    @Expose
    private String invoiceNo;
    @SerializedName("InvoiceDate")
    @Expose
    private String invoiceDate;
    @SerializedName("Remark")
    @Expose
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
