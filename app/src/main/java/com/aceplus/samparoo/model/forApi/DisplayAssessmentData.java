package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 3/20/17.
 */

public class DisplayAssessmentData {

    @SerializedName("OutletVisibility")
    @Expose
    private List<DisplayAssessment> displayAssessment = null;

    public List<DisplayAssessment> getDisplayAssessment() {
        return displayAssessment;
    }

    public void setDisplayAssessment(List<DisplayAssessment> displayAssessment) {
        this.displayAssessment = displayAssessment;
    }


}
