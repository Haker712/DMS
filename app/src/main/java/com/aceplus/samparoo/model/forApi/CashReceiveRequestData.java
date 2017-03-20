package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 3/17/17.
 *
 * Cash receive request data
 */

public class CashReceiveRequestData {

    /**
     * TCashReceive
     */
    @SerializedName("TCashReceive")
    @Expose
    private List<CashReceiveApi> cashReceiveApiList = null;

    public List<CashReceiveApi> getCashReceiveApiList() {
        return cashReceiveApiList;
    }

    public void setCashReceiveApiList(List<CashReceiveApi> cashReceiveApiList) {
        this.cashReceiveApiList = cashReceiveApiList;
    }
}
