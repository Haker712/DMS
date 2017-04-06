package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 4/6/17.
 */

public class CompanyInfromationData {

    @SerializedName("CompanyInformation")
    @Expose
    private List<CompanyInformation> companyInformation = null;

    public List<CompanyInformation> getCompanyInformation() {
        return companyInformation;
    }

    public void setCompanyInformation(List<CompanyInformation> companyInformation) {
        this.companyInformation = companyInformation;
    }

}
