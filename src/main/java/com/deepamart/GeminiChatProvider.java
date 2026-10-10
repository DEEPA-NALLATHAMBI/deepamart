package com.deepamart;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class GeminiChatProvider implements ChatProvider {

    private static final String API_URL =
        "https://generativelanguage.googleapis.com/v1beta/models/"
        + "gemini-3.8-flash:generateContent";

    @Override
    public String getReply(String userMessage, String context) {

        String apiKey = System.getenv("GEMINI_API_KEY");

        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException(
                "Gemini API key is not configured."
            );
        }

        HttpURLConnection connection = null;

        try {
            URL url = new URL(API_URL);

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            connection.setDoOutput(true);

            connection.setRequestProperty(
                "Content-Type",
                "application/json; charset=UTF-8"
            );

            connection.setRequestProperty(
                "x-goog-api-key",
                apiKey
            );

            String systemPrompt =
                "You are the official AI shopping assistant for Deepa Mart. "
                + "Answer questions about shopping, products, product "
                + "listings, cart, checkout, orders, sellers, reviews, "
                + "ratings and marketplace navigation. "
                + "Keep answers friendly, clear and concise. "
                + "Do not invent product prices, stock, order status, "
                + "refund policies or account information. "
                + "If you do not have verified information, say so. "
                + "Never ask users to share passwords, API keys or "
                + "payment credentials. "
                + "For unrelated questions, politely guide the user "
                + "back to Deepa Mart topics.";

            String question = userMessage;

            if (context != null && !context.trim().isEmpty()) {
                question =
                    "Verified application context:\n"
                    + context
                    + "\n\nUser question:\n"
                    + userMessage;
            }

            JsonObject requestBody = new JsonObject();

            JsonObject systemInstruction = new JsonObject();
            JsonArray systemParts = new JsonArray();
            JsonObject systemText = new JsonObject();

            systemText.addProperty("text", systemPrompt);
            systemParts.add(systemText);
            systemInstruction.add("parts", systemParts);

            requestBody.add(
                "systemInstruction",
                systemInstruction
            );

            JsonObject userContent = new JsonObject();
            JsonArray userParts = new JsonArray();
            JsonObject userText = new JsonObject();

            userText.addProperty("text", question);
            userParts.add(userText);

            userContent.add("parts", userParts);

            JsonArray contents = new JsonArray();
            contents.add(userContent);

            requestBody.add("contents", contents);

            JsonObject generationConfig = new JsonObject();
            generationConfig.addProperty("temperature", 0.4);
            generationConfig.addProperty("maxOutputTokens", 300);

            requestBody.add(
                "generationConfig",
                generationConfig
            );

            byte[] requestBytes =
                requestBody.toString()
                    .getBytes(StandardCharsets.UTF_8);

            connection.setFixedLengthStreamingMode(
                requestBytes.length
            );

            try (var output = connection.getOutputStream()) {
                output.write(requestBytes);
            }

            int statusCode = connection.getResponseCode();

            java.io.InputStream responseStream;

            if (statusCode >= 200 && statusCode < 300) {
                responseStream = connection.getInputStream();
            } else {
                responseStream = connection.getErrorStream();

                if (responseStream != null) {
                    responseStream.close();
                }

                throw new IOException(
                    "Gemini API returned HTTP " + statusCode
                );
            }

            String responseText;

            try (Scanner scanner = new Scanner(
                    responseStream,
                    StandardCharsets.UTF_8.name())) {

                scanner.useDelimiter("\\A");

                responseText =
                    scanner.hasNext() ? scanner.next() : "";
            }

            JsonObject responseJson =
                JsonParser.parseString(responseText)
                    .getAsJsonObject();

            if (!responseJson.has("candidates")
                    || responseJson.get("candidates").isJsonNull()) {

                throw new IOException(
                    "Gemini did not return a response."
                );
            }

            JsonArray candidates =
                responseJson.getAsJsonArray("candidates");

            if (candidates.isEmpty()) {
                throw new IOException(
                    "Gemini returned an empty response."
                );
            }

            JsonObject candidate =
                candidates.get(0).getAsJsonObject();

            if (!candidate.has("content")
                    || candidate.get("content").isJsonNull()) {

                throw new IOException(
                    "Gemini response content is unavailable."
                );
            }

            JsonObject content =
                candidate.getAsJsonObject("content");

            JsonArray parts =
                content.getAsJsonArray("parts");

            if (parts == null || parts.isEmpty()) {
                throw new IOException(
                    "Gemini response contains no text."
                );
            }

            StringBuilder answer = new StringBuilder();

            for (int i = 0; i < parts.size(); i++) {

                JsonObject part =
                    parts.get(i).getAsJsonObject();

                if (part.has("text")
                        && !part.get("text").isJsonNull()) {

                    answer.append(
                        part.get("text").getAsString()
                    );
                }
            }

            String reply = answer.toString().trim();

            if (reply.isEmpty()) {
                throw new IOException(
                    "Gemini returned an empty text response."
                );
            }

            return reply;

        } catch (IOException e) {

            throw new RuntimeException(
                "Unable to get a response from Gemini.",
                e
            );

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}