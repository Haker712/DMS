package com.aceplus.samparoo.model.forApi;

/**
 * Created by phonelin on 3/20/17.
 */

public class DisplayAssessment {

    String InvoiceNo;
    String InvoiceDate;
    String CustomerId;

    String SaleManId;
    String Image;

    public String getInvoice_No() {
        return InvoiceNo;
    }

    public void setInvoice_No(String invoice_No) {
        this.InvoiceNo = invoice_No;
    }

    public String getInvoice_Date() {
        return InvoiceDate;
    }

    public void setInvoice_Date(String invoice_Date) {
        this.InvoiceDate = invoice_Date;
    }

    public String getCustomer_Id() {
        return CustomerId;
    }

    public void setCustomer_Id(String customer_Id) {
        this.CustomerId = customer_Id;
    }

    public String getSaleman_Id() {
        return SaleManId;
    }

    public void setSaleman_Id(String saleman_Id) {
        this.SaleManId = saleman_Id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }



}
