package com.university.ilya.model;

import org.joda.money.Money;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Ilya_Bondarenko
 */
public class Order extends BaseEntity {

    private List<Product> products;
    private Money totalPrice;
    private DateTime time;

    public Order() {
    }

    public Order(List<Product> products, Money totalPrice, DateTime time) {
        this.products = products;
        this.totalPrice = totalPrice;
        this.time = time;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
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
