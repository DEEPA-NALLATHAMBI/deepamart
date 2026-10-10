package com.deepamart;

public class ChatService {

    private final ChatProvider provider;

    public ChatService() {

        String selectedProvider =
            System.getProperty("ai.chatbot.provider");

        if (selectedProvider == null
                || selectedProvider.trim().isEmpty()) {

            selectedProvider =
                System.getenv("AI_CHATBOT_PROVIDER");
        }

        if (selectedProvider == null
                || selectedProvider.trim().isEmpty()) {

            selectedProvider = "gemini";
        }

        if ("mock".equalsIgnoreCase(selectedProvider)) {

            provider = new MockChatProvider();

        } else if ("gemini".equalsIgnoreCase(selectedProvider)) {

            provider = new GeminiChatProvider();

        } else {

            throw new IllegalArgumentException(
                "Unsupported chatbot provider: "
                + selectedProvider
            );
        }
    }

    public String getReply(
            String userMessage,
            String context) {

        return provider.getReply(userMessage, context);
    }
}
