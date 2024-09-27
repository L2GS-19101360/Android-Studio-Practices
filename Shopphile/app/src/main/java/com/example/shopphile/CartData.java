package com.example.shopphile;

public class CartData {
    private int productCartImage;
    private String productCartName;
    private float productCartPrice;
    private int productCartQuantity;

    public CartData(int productCartImage, String productCartName, float productCartPrice, int productCartQuantity) {
        this.productCartImage = productCartImage;
        this.productCartName = productCartName;
        this.productCartPrice = productCartPrice;
        this.productCartQuantity = productCartQuantity;
    }

    public int getProductCartImage() {
        return productCartImage;
    }

    public String getProductCartName() {
        return productCartName;
    }

    public float getProductCartPrice() {
        return productCartPrice;
    }

    public int getProductCartQuantity() {
        return productCartQuantity;
    }

    public float getProductCartTotalPrice() {
        return productCartPrice * productCartQuantity;
    }
}
