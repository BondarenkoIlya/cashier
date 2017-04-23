package com.university.ilya.model;

/**
 * @author Ilya_Bondarenko
 */
public class Consignment extends BaseEntity {

    private Product product;
    private int numberInPackage;
    private int actualNumber;

    public Consignment() {
    }

    public Consignment(Product product, int numberInPackage, int actualNumber) {
        this.product = product;
        this.numberInPackage = numberInPackage;
        this.actualNumber = actualNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNumberInPackage() {
        return numberInPackage;
    }

    public void setNumberInPackage(int numberInPackage) {
        this.numberInPackage = numberInPackage;
    }

    public int getActualNumber() {
        return actualNumber;
    }

    public void setActualNumber(int actualNumber) {
        this.actualNumber = actualNumber;
    }
}
