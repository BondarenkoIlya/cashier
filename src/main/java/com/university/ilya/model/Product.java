package com.university.ilya.model;

import org.joda.money.Money;

/**
 * @author Ilya_Bondarenko
 */
public class Product extends BaseEntity {
    public static final String currency = "KZT";

    private String name;
    private Money price;
    private int barcode;

    public Product() {
    }

    public Product(String name, Money price, int barcode) {
        this.name = name;
        this.price = price;
        this.barcode = barcode;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }
}
