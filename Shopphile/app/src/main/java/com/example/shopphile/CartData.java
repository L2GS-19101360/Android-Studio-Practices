package com.example.shopphile;

public class CartData {

    private int productCartImage;
    private String productCartName;
    private float productCartTotalPrice; // Keep this for total price
    private int productCartQuantity;

    public CartData(int productCartImage, String productCartName, float productPrice, int productCartQuantity) {
        this.productCartImage = productCartImage;
        this.productCartName = productCartName;
        this.productCartQuantity = productCartQuantity;
        this.productCartTotalPrice = productPrice * productCartQuantity; // Calculate total price
    }

    public int getProductCartImage() {
        return productCartImage;
    }

    public String getProductCartName() {
        return productCartName;
    }

    public float getProductCartTotalPrice() {
        return productCartTotalPrice; // Return the calculated total price
    }

    public int getProductCartQuantity() {
        return productCartQuantity;
    }

}
