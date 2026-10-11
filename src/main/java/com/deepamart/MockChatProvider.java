package com.deepamart;

import java.util.Locale;

public class MockChatProvider implements ChatProvider {

    @Override
    public String getReply(String userMessage, String context) {

        if (userMessage == null || userMessage.trim().isEmpty()) {
            return "Please enter your question about Deepa Mart.";
        }

        String message = userMessage.toLowerCase(Locale.ROOT).trim();

        if (message.contains("hello")
                || message.contains("hi")
                || message.contains("hey")) {
            return "Hello! Welcome to Deepa Mart. "
                    + "How can I help you with shopping today?";
        }

        if (message.contains("product")
                || message.contains("available")
                || message.contains("listing")) {
            return "You can view the available products on the "
                    + "Deepa Mart Products page. Product prices and "
                    + "stock should be checked on the listing.";
        }

        if (message.contains("search")
                || message.contains("category")) {
            return "Open the Products page to browse products. "
                    + "Use the available search or category filters "
                    + "to find what you need.";
        }

        if (message.contains("cart")
                || message.contains("add to cart")) {
            return "Open the Products page, choose a product, "
                    + "and select Add to Cart. Review your cart "
                    + "before checkout.";
        }

        if (message.contains("checkout")
                || message.contains("payment")
                || message.contains("buy")) {
            return "Open your cart, review the items and total, "
                    + "then follow the checkout steps to place "
                    + "your order.";
        }

        if (message.contains("track")
                || message.contains("order")
                || message.contains("delivery")) {
            return "To check your order, log in to Deepa Mart "
                    + "and open My Orders or Order History. "
                    + "The page shows the order information available "
                    + "for your account.";
        }

        if (message.contains("seller")
                || message.contains("sell")) {
            return "Sellers can log in to their seller account "
                    + "to manage product listings and view incoming "
                    + "orders.";
        }

        if (message.contains("review")
                || message.contains("rating")) {
            return "Open the relevant product page to check its "
                    + "reviews and ratings. Follow the site's review "
                    + "option if you are eligible to submit a review.";
        }

        if (message.contains("login")
                || message.contains("sign in")
                || message.contains("account")) {
            return "Use the Login page to access your Deepa Mart "
                    + "account. If you do not have an account, "
                    + "use the Signup page.";
        }

        return "I am the Deepa Mart shopping assistant. "
                + "You can ask me about products, search, cart, "
                + "checkout, orders, sellers, login, or reviews.";
    }
}