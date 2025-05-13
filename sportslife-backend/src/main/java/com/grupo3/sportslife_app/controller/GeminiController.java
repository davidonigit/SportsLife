package com.grupo3.sportslife_app.controller;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/gemini")
public class GeminiController {
    
    private final VertexAiGeminiChatModel chatModel;

    public GeminiController(VertexAiGeminiChatModel chatModel){
        this.chatModel = chatModel;
    }

    @RequestMapping("/")
    public String chat() {
        var prompt = new Prompt("Crie uma tabela com dias da semana, mas vazia");

        return chatModel
            .call(prompt)
            .getResult()
            .getOutput()
            .getText();
    }
    
}