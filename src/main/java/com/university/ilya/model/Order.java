package com.university.ilya.model;

import org.joda.money.Money;

import java.util.List;

/**
 * @author Ilya_Bondarenko
 */
public class Order extends BaseEntity {
    private List<Product> products;
    private Money totalPrice;

    public Order(List<Product> products, Money totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Money totalPrice) {
        this.totalPrice = totalPrice;
    }
}
