package com.deepamart;

import java.util.Locale;

public class MockChatProvider implements ChatProvider {

    @Override
    public String getReply(String userMessage, String context) {

        if (userMessage == null || userMessage.trim().isEmpty()) {
            return "Please type a question about Deepa Mart.";
        }

        String message = userMessage.toLowerCase(Locale.ROOT);

        if (message.contains("hello") || message.contains("hi")
                || message.contains("hey")) {
            return "Hello! Welcome to Deepa Mart. How can I help you?";
        }

        if (message.contains("product") || message.contains("search")
                || message.contains("available")) {
            return "Visit the Products page to browse available items. "
                    + "Product availability depends on the listed stock.";
        }

        if (message.contains("cart")) {
            return "Open the Cart page to review your selected products "
                    + "and update quantities before checkout.";
        }

        if (message.contains("order") || message.contains("track")) {
            return "Visit My Orders to check your order history and "
                    + "available order details.";
        }

        if (message.contains("payment") || message.contains("pay")
                || message.contains("checkout")) {
            return "Open Checkout to continue placing your order. "
                    + "Follow the payment options displayed by Deepa Mart.";
        }

        if (message.contains("seller") || message.contains("sell")) {
            return "Sellers can use the Seller Dashboard to manage "
                    + "their product listings and view customer orders.";
        }

        if (message.contains("review") || message.contains("rating")
                || message.contains("star")) {
            return "Open the Reviews section for the product. "
                    + "Review eligibility depends on your order and app rules.";
        }

        if (message.contains("return") || message.contains("refund")) {
            return "Please check Deepa Mart's stated return or refund policy. "
                    + "I cannot confirm a refund without the order details.";
        }

        if (message.contains("admin")) {
            return "The Admin Dashboard is for authorised administrators "
                    + "to manage users and oversee the marketplace.";
        }

        return "I can help with products, cart, orders, checkout, sellers, "
                + "reviews and general Deepa Mart questions. What would "
                + "you like to know?";
    }
}
