package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 2/13/17.
 */

public class DataforSaleUpload {

    @SerializedName("invoice")
    @Expose
    private List<Invoice> invoice = null;
    @SerializedName("invoice_present")
    @Expose
    private List<InvoicePresent> invoicePresent = null;

    public List<Invoice> getInvoice() {
        return invoice;
    }

    public void setInvoice(List<Invoice> invoice) {
        this.invoice = invoice;
    }

    public List<InvoicePresent> getInvoicePresent() {
        return invoicePresent;
    }

    public void setInvoicePresent(List<InvoicePresent> invoicePresent) {
        this.invoicePresent = invoicePresent;
    }


}
