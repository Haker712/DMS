package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yma on 3/15/17.
 *
 * Customer Balance for API
 */

public class CustomerBalanceForApi {

    @SerializedName("Id")
    @Expose
    int id;

    @SerializedName("CustomerId")
    @Expose
    int customerId;

    @SerializedName("CurrencyId")
    @Expose
    int currencyId;

    @SerializedName("OpeningBalance")
    @Expose
    double openingBalance;

    @SerializedName("Balance")
    @Expose
    double balance;

    /**
     * Getter method for id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method for id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method for customerId
     *
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter method for customerId
     *
     * @param customerId customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method for currencyId
     *
     * @return currencyId
     */
    public int getCurrencyId() {
        return currencyId;
    }

    /**
     * Setter method for currencyId
     *
     * @param currencyId currencyId
     */
    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * Getter method for openingBalance
     *
     * @return openingBalance
     */
    public double getOpeningBalance() {
        return openingBalance;
    }

    /**
     * Setter method for openingBalance
     *
     * @param openingBalance openingBalance
     */
    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    /**
     * Getter method for balance
     *
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Setter method for balance.
     *
     * @param balance balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
