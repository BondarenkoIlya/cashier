package com.university.ilya.model;

import org.joda.money.Money;

/**
 * @author Ilya_Bondarenko
 */
public class Product extends BaseEntity {

    private String name;
    private Money price;
    private byte[] barcode;

    public Product() {
    }

    public Product(String name, Money price, byte[] barcode) {
        this.name = name;
        this.price = price;
        this.barcode = barcode;
    }

    public byte[] getBarcode() {
        return barcode;
    }

    public void setBarcode(byte[] barcode) {
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
