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


}
