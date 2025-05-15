package com.grupo3.sportslife_app.service;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.stereotype.Service;

@Service
public class FachadaLLM {
    
    private final VertexAiGeminiChatModel chatModel;

    public FachadaLLM(VertexAiGeminiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String chat(String message) {
        var prompt = new Prompt(message);

        return chatModel
            .call(prompt)
            .getResult()
            .getOutput()
            .getText();
    }
}
