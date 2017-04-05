package com.aceplus.samparoo.model.forApi;

/**
 * Created by haker on 4/5/17.
 */

public class Currency {
    int Id;
    String Currency;
    String Description;
    String CuponStatus;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCuponStatus() {
        return CuponStatus;
    }

    public void setCuponStatus(String cuponStatus) {
        CuponStatus = cuponStatus;
    }
}
