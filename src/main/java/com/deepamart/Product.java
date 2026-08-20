package com.deepamart;

public class Product {

    private int productId;
    private String productName;
    private String category;
    private double price;
    private int stock;
    private String imageUrl;

    public Product(int productId, String productName,
                   String category, double price,
                   int stock, String imageUrl) {

        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
