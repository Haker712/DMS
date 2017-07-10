package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aceplus_mobileteam on 7/10/17.
 */

public class CompetitorRequestData {

    @SerializedName("CompetitorActivities")
    @Expose
    List<Competitor_Activity> competitorActivityList;

    public List<Competitor_Activity> getCompetitorActivityList() {
        return competitorActivityList;
    }

    public void setCompetitorActivityList(List<Competitor_Activity> competitorActivityList) {
        this.competitorActivityList = competitorActivityList;
    }
}
