package com.example.shopphile;

public class CartData {

    private int productCartImage;
    private String productCartName;
    private float productCartTotalPrice;
    private int productCartQuantity;

    public CartData(int productCartImage, String productCartName, float productCartTotalPrice, int productCartQuantity) {
        this.productCartImage = productCartImage;
        this.productCartName = productCartName;
        this.productCartTotalPrice = productCartTotalPrice;
        this.productCartQuantity = productCartQuantity;
    }

    public int getProductCartImage() {
        return productCartImage;
    }

    public String getProductCartName() {
        return productCartName;
    }

    public float getProductCartTotalPrice() {
        return productCartTotalPrice;
    }

    public int getProductCartQuantity() {
        return productCartQuantity;
    }

}
