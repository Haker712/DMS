package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by haker on 4/4/17.
 */

public class DataForSaleManRoute {
    @SerializedName("ERoute_Report")
    @Expose
    private List<ERouteReport> eRouteReport = null;

    public List<ERouteReport> getERouteReport() {
        return eRouteReport;
    }

    public void setERouteReport(List<ERouteReport> eRouteReport) {
        this.eRouteReport = eRouteReport;
    }
}
