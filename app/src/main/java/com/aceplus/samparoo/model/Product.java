package com.aceplus.samparoo.model;

import java.io.Serializable;

public class Product implements Cloneable, Serializable {

    private String id, name;
    private Double price;
    private Double purchasePrice;
    private String discountType;
    private int remainingQty, soldQty;

    private String um;

    private int stockId;

    public Product(String id, String name, Double price, Double purchasePrice, String discountType, int remainingQty) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.purchasePrice = purchasePrice;
        this.discountType = discountType;
        this.remainingQty = remainingQty;

        soldQty = 0;
    }

    public String getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public Double getPrice() {

        return price;
    }

    public Double getPurchasePrice() {

        return purchasePrice;
    }

    public String getDiscountType() {

        return discountType;
    }

    public int getRemainingQty() {

        return remainingQty;
    }

    public int getSoldQty() {

        return soldQty;
    }

    public void changeDiscountToI() {

        discountType = "i";
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public boolean setSoldQty(int soldQty) {

//		if (soldQty > remainingQty) {
//			
//			return false;
//		}

        this.soldQty = soldQty;
        this.remainingQty -= soldQty;

        return true;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }
}
