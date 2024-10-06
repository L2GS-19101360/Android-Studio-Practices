package com.example.shopphile;

public class ProductData {

    private int productImage;
    private String productName;
    private float productPrice;
    private String productDescription;

    public ProductData(int productImage, String productName, float productPrice, String productDescription) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
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

    public String getProductDescription() {
        return productDescription;
    }
}
