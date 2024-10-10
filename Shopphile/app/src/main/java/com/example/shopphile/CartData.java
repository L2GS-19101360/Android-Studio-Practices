package com.example.shopphile;

public class CartData {
    private int productCartId;
    private int productCartImage;
    private String productCartName;
    private float productCartPrice;
    private int productCartQuantity;

    public CartData(int productCartId, int productCartImage, String productCartName, float productCartPrice, int productCartQuantity) {
        this.productCartId = productCartId;
        this.productCartImage = productCartImage;
        this.productCartName = productCartName;
        this.productCartPrice = productCartPrice;
        this.productCartQuantity = productCartQuantity;
    }

    public int getProductCartId() {
        return productCartId;
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
        return productCartPrice;
    }
}
