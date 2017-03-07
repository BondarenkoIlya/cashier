package com.university.ilya.model;

/**
 * @author Ilya_Bondarenko
 */
public class Consignment extends BaseEntity {

    private Product product;
    private int number;

    public Consignment() {
    }

    public Consignment(Product product, int number) {
        this.product = product;
        this.number = number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
