package com.deepamart;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@WebServlet("/api/chat")
public class ChatServlet extends HttpServlet {

    private static final int MAX_MESSAGE_LENGTH = 500;
    private static final int MAX_MESSAGES = 10;
    private static final int MAX_CACHE_ENTRIES = 20;
    private static final long WINDOW_MS = 60_000L;

    private final ChatService chatService = new ChatService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setCharacterEncoding(
            StandardCharsets.UTF_8.name()
        );

        response.setContentType(
            "application/json;charset=UTF-8"
        );

        String message;

        try {

            message = readMessage(request);

        } catch (IllegalArgumentException e) {

            writeReply(
                response,
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid request. Please send a valid message."
            );

            return;
        }

        if (message == null) {

            writeReply(
                response,
                HttpServletResponse.SC_BAD_REQUEST,
                "Please enter a message."
            );

            return;
        }

        message = message.trim();

        if (message.isEmpty()
                || message.length() > MAX_MESSAGE_LENGTH) {

            writeReply(
                response,
                HttpServletResponse.SC_BAD_REQUEST,
                "Message must contain 1 to 500 characters."
            );

            return;
        }

        HttpSession session = request.getSession(true);

        String cacheKey = message
            .toLowerCase(Locale.ROOT)
            .replaceAll("\\s+", " ")
            .trim();

        String cachedReply;

        synchronized (session) {

            long now = System.currentTimeMillis();

            @SuppressWarnings("unchecked")
            Deque<Long> timestamps =
                (Deque<Long>) session.getAttribute(
                    "chatTimestamps"
                );

            if (timestamps == null) {

                timestamps = new ArrayDeque<>();

                session.setAttribute(
                    "chatTimestamps",
                    timestamps
                );
            }

            while (!timestamps.isEmpty()
                    && now - timestamps.peekFirst()
                        >= WINDOW_MS) {

                timestamps.removeFirst();
            }

            if (timestamps.size() >= MAX_MESSAGES) {

                writeReply(
                    response,
                    429,
                    "You have reached the chat limit. "
                    + "Please wait a minute and try again."
                );

                return;
            }

            timestamps.addLast(now);

            @SuppressWarnings("unchecked")
            Map<String, String> cache =
                (Map<String, String>) session.getAttribute(
                    "chatReplyCache"
                );

            if (cache == null) {

                cache = new LinkedHashMap<>();

                session.setAttribute(
                    "chatReplyCache",
                    cache
                );
            }

            cachedReply = cache.get(cacheKey);
        }

        if (cachedReply != null) {

            writeReply(
                response,
                HttpServletResponse.SC_OK,
                cachedReply
            );

            return;
        }

        String reply;

        try {

            reply = chatService.getReply(message, "");

            if (reply == null || reply.trim().isEmpty()) {

                throw new IllegalStateException(
                    "Empty chatbot response."
                );
            }

        } catch (Exception e) {

            getServletContext().log(
                "Chatbot provider request failed.",
                e
            );

            writeReply(
                response,
                HttpServletResponse.SC_OK,
                "Sorry, the AI assistant is temporarily "
                + "unavailable. Please try again later."
            );

            return;
        }

        synchronized (session) {

            @SuppressWarnings("unchecked")
            Map<String, String> cache =
                (Map<String, String>) session.getAttribute(
                    "chatReplyCache"
                );

            if (cache != null) {

                if (cache.size() >= MAX_CACHE_ENTRIES
                        && !cache.containsKey(cacheKey)) {

                    String oldestKey =
                        cache.keySet().iterator().next();

                    cache.remove(oldestKey);
                }

                cache.put(cacheKey, reply);
            }
        }

        writeReply(
            response,
            HttpServletResponse.SC_OK,
            reply
        );
    }

    private String readMessage(
            HttpServletRequest request) throws IOException {

        String contentType = request.getContentType();

        if (contentType != null
                && contentType.toLowerCase(Locale.ROOT)
                    .contains("application/json")) {

            StringBuilder body = new StringBuilder();

            try (BufferedReader reader = request.getReader()) {

                char[] buffer = new char[1024];

                int count;

                while ((count = reader.read(buffer)) != -1) {

                    body.append(buffer, 0, count);

                    if (body.length() > 10_000) {

                        throw new IllegalArgumentException(
                            "Request body is too large."
                        );
                    }
                }
            }

            try {

                JsonObject json =
                    JsonParser.parseString(body.toString())
                        .getAsJsonObject();

                if (!json.has("message")
                        || json.get("message").isJsonNull()) {

                    return null;
                }

                if (!json.get("message").isJsonPrimitive()) {
                    return null;
                }

                return json.get("message").getAsString();

            } catch (RuntimeException e) {

                throw new IllegalArgumentException(
                    "Invalid JSON request."
                );
            }
        }

        return request.getParameter("message");
    }

    private void writeReply(
            HttpServletResponse response,
            int status,
            String reply) throws IOException {

        response.setStatus(status);

        JsonObject json = new JsonObject();

        json.addProperty("reply", reply);

        response.getWriter().write(
            gson.toJson(json)
        );
    }
}