package com.aceplus.samparoo.model.forApi;

/**
 * Created by phonelin on 3/20/17.
 */

public class DisplayAssessment {

    String invoice_No;
    String invoice_Date;
    String customer_Id;

    public String getInvoice_No() {
        return invoice_No;
    }

    public void setInvoice_No(String invoice_No) {
        this.invoice_No = invoice_No;
    }

    public String getInvoice_Date() {
        return invoice_Date;
    }

    public void setInvoice_Date(String invoice_Date) {
        this.invoice_Date = invoice_Date;
    }

    public String getCustomer_Id() {
        return customer_Id;
    }

    public void setCustomer_Id(String customer_Id) {
        this.customer_Id = customer_Id;
    }

    public String getSaleman_Id() {
        return saleman_Id;
    }

    public void setSaleman_Id(String saleman_Id) {
        this.saleman_Id = saleman_Id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String saleman_Id;
    String image;

}
