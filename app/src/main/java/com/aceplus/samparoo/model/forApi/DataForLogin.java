package com.aceplus.samparoo.model.forApi;

import com.aceplus.samparoo.model.Route;
import com.aceplus.samparoo.model.RouteAssign;
import com.aceplus.samparoo.model.RouteSchedule;
import com.aceplus.samparoo.model.SaleMan;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by haker on 2/7/17.
 */
public class DataForLogin {

    @SerializedName("SaleMan")
    @Expose
    private List<SaleMan> saleMan = null;
    @SerializedName("Route")
    @Expose
    private List<Route> route = null;
    @SerializedName("RouteAssign")
    @Expose
    private List<RouteAssign> routeAssign = null;
    @SerializedName("RouteSchedule")
    @Expose
    private List<RouteSchedule> routeSchedule = null;

    public List<SaleMan> getSaleMan() {
        return saleMan;
    }

    public void setSaleMan(List<SaleMan> saleMan) {
        this.saleMan = saleMan;
    }

    public List<Route> getRoute() {
        return route;
    }

    public void setRoute(List<Route> route) {
        this.route = route;
    }

    public List<RouteAssign> getRouteAssign() {
        return routeAssign;
    }

    public void setRouteAssign(List<RouteAssign> routeAssign) {
        this.routeAssign = routeAssign;
    }

    public List<RouteSchedule> getRouteSchedule() {
        return routeSchedule;
    }

    public void setRouteSchedule(List<RouteSchedule> routeSchedule) {
        this.routeSchedule = routeSchedule;
    }
}
