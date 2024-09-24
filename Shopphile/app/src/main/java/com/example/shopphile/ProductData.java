package com.example.shopphile;

public class ProductData {

    private int productImage;
    private String productName;
    private float productPrice;

    public ProductData(int productImage, String productName, float productPrice) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public int getProductImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }

    public float getProductPrice() {
        return productPrice;
    }
}
