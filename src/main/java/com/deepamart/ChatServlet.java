package com.deepamart;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/api/chat")
public class ChatServlet extends HttpServlet {

    private static final int MAX_MESSAGE_LENGTH = 500;
    private static final int MAX_MESSAGES = 10;
    private static final int MAX_CACHE_ENTRIES = 20;
    private static final long WINDOW_MS = 60_000L;

    private final ChatService chatService = new ChatService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        res.setContentType("application/json;charset=UTF-8");

        String message;
        try {
            message = readMessage(req);
        } catch (IllegalArgumentException e) {
            writeReply(res, 400, "Invalid request. Please send a valid message.");
            return;
        }

        if (message == null || message.trim().isEmpty()
                || message.trim().length() > MAX_MESSAGE_LENGTH) {
            writeReply(res, 400, "Message must contain 1 to 500 characters.");
            return;
        }

        message = message.trim();
        HttpSession session = req.getSession(true);
        String key = message.toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");

        synchronized (session) {
            long now = System.currentTimeMillis();
            @SuppressWarnings("unchecked")
            Deque<Long> times = (Deque<Long>) session.getAttribute("chatTimestamps");

            if (times == null) {
                times = new ArrayDeque<>();
                session.setAttribute("chatTimestamps", times);
            }

            while (!times.isEmpty() && now - times.peekFirst() >= WINDOW_MS)
                times.removeFirst();

            if (times.size() >= MAX_MESSAGES) {
                writeReply(res, 429, "Chat limit reached. Please wait a minute.");
                return;
            }
            times.addLast(now);

            @SuppressWarnings("unchecked")
            Map<String, String> cache =
                    (Map<String, String>) session.getAttribute("chatReplyCache");

            if (cache != null && cache.containsKey(key)) {
                writeReply(res, 200, cache.get(key));
                return;
            }
        }

        String reply = getQuickReply(message);

        if (reply != null) {
            cacheReply(session, key, reply);
            writeReply(res, 200, reply);
            return;
        }

        String productContext;
        try {
            List<Product> products = new ProductDAO().getAllProducts();
            StringBuilder catalog = new StringBuilder("Deepa Mart product catalog:\n");

            for (Product p : products) {
                catalog.append(p.getProductName())
                    .append(" | Category: ").append(p.getCategory())
                    .append(" | Price: ₹")
                    .append(String.format(Locale.ROOT, "%.2f", p.getPrice()))
                    .append(" | Stock: ").append(p.getStock()).append("\n");
            }
            productContext = catalog.toString();

            String text = message.toLowerCase(Locale.ROOT);
            if (text.contains("product") &&
                    (text.contains("available") || text.contains("list")
                    || text.contains("show") || text.contains("stock")
                    || text.contains("price") || text.contains("them"))) {
                reply = products.isEmpty() ? "No products are currently listed."
                        : catalog.toString();
                cacheReply(session, key, reply);
                writeReply(res, 200, reply);
                return;
            }
        } catch (Exception e) {
            getServletContext().log("Could not load product catalog.", e);
            writeReply(res, 200, "Sorry, I couldn't load products right now.");
            return;
        }

        try {
            reply = chatService.getReply(message, productContext);
            if (reply == null || reply.trim().isEmpty())
                throw new IllegalStateException("Empty chatbot response.");
        } catch (Exception e) {
            getServletContext().log("Chatbot provider request failed.", e);
            writeReply(res, 200, "Sorry, the AI assistant is temporarily unavailable.");
            return;
        }

        cacheReply(session, key, reply);
        writeReply(res, 200, reply);
    }

    private String getQuickReply(String message) {
        String s = message.toLowerCase(Locale.ROOT).replaceAll("\\s+", " ").trim();

        if (has(s, "what can you help", "what can you do", "your features"))
            return "Welcome to Deepa Mart! I can help with products, prices, stock, cart, checkout, orders, accounts, and reviews.";

        if (has(s, "add to cart", "how do i add", "how can i add"))
            return "Open a product and click Add to Cart. Follow the options shown on the page.";

        if (has(s, "view cart", "show my cart", "open my cart", "see my cart"))
            return "Click the Cart option to review your selected products.";

        if (has(s, "remove from cart", "remove item", "delete from cart"))
            return "Open your cart and use the Remove or Delete option next to the product, if displayed.";

        if (has(s, "change quantity", "update quantity", "increase quantity", "decrease quantity"))
            return "Open your cart and use the quantity controls shown next to the product.";

        if (has(s, "checkout", "place an order", "place order", "how to order", "how do i order"))
            return "Add products to your cart, open the cart, select Checkout, and follow the steps to confirm your order.";

        if (has(s, "my orders", "view my orders", "order history", "previous orders", "past orders"))
            return "Open My Orders or Order History. You may need to log in first.";

        if (has(s, "order status", "track my order", "track order", "where is my order"))
            return "Open My Orders and select the order to see its available status.";

        if (has(s, "create an account", "register", "sign up", "signup"))
            return "Open the Register or Sign Up page and complete the required fields.";

        if (has(s, "how do i log in", "how can i log in", "login", "log in", "sign in"))
            return "Open the Login page and enter your registered account details.";

        if (has(s, "log out", "logout", "sign out"))
            return "Use the Logout or Sign Out option.";

        if (has(s, "write a review", "give a review", "product review", "leave a review"))
            return "Open the product page and look for its review section.";

        if (has(s, "rate a product", "give a rating", "product rating", "how to rate"))
            return "Open the product page and look for its rating option.";

        return null;
    }

    private boolean has(String text, String... words) {
        for (String word : words)
            if (text.contains(word)) return true;
        return false;
    }

    private void cacheReply(HttpSession session, String key, String reply) {
        synchronized (session) {
            @SuppressWarnings("unchecked")
            Map<String, String> cache =
                    (Map<String, String>) session.getAttribute("chatReplyCache");

            if (cache == null) {
                cache = new LinkedHashMap<>();
                session.setAttribute("chatReplyCache", cache);
            }

            if (cache.size() >= MAX_CACHE_ENTRIES && !cache.containsKey(key))
                cache.remove(cache.keySet().iterator().next());

            cache.put(key, reply);
        }
    }

    private String readMessage(HttpServletRequest req) throws IOException {
        String type = req.getContentType();

        if (type != null && type.toLowerCase(Locale.ROOT).contains("application/json")) {
            StringBuilder body = new StringBuilder();
            try (BufferedReader reader = req.getReader()) {
                char[] buffer = new char[1024];
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    body.append(buffer, 0, n);
                    if (body.length() > 10_000)
                        throw new IllegalArgumentException("Request too large.");
                }
            }

            try {
                JsonObject json = JsonParser.parseString(body.toString()).getAsJsonObject();
                if (!json.has("message") || json.get("message").isJsonNull()
                        || !json.get("message").isJsonPrimitive()) return null;
                return json.get("message").getAsString();
            } catch (RuntimeException e) {
                throw new IllegalArgumentException("Invalid JSON.");
            }
        }
        return req.getParameter("message");
    }

    private void writeReply(HttpServletResponse res, int status, String reply)
            throws IOException {
        res.setStatus(status);
        JsonObject json = new JsonObject();
        json.addProperty("reply", reply);
        res.getWriter().write(gson.toJson(json));
    }
}