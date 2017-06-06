package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aceplus_mobileteam on 6/5/17.
 */

public class TSaleFeedbackData {

    @SerializedName("invoice_feedback")
    @Expose
    private List<TSaleFeedback> feedBackList;

    public List<TSaleFeedback> getFeedBackList() {
        return feedBackList;
    }

    public void setFeedBackList(List<TSaleFeedback> feedBackList) {
        this.feedBackList = feedBackList;
    }
}
