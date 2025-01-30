package com.example.listadd.model;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("product_name")  // Use correct JSON key
    private String productName;

    @SerializedName("price")
    private double price;

    @SerializedName("tax")
    private double tax;

    @SerializedName("image")
    private String image;

    // Constructor
    public Product(String productName, String price, double tax, double image) {
        this.productName = productName;
        this.price = Double.parseDouble(price);
        this.tax = tax;
        this.image = String.valueOf(image);
    }

    // Getters
    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public double getTax() {
        return tax;
    }

    public String getImage() {
        return image;
    }
}
