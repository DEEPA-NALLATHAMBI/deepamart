package com.deepamart;

public interface ChatProvider {
    String getReply(String userMessage, String context);
}
