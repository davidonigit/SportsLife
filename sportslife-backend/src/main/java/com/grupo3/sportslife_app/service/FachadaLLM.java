package com.grupo3.sportslife_app.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class FachadaLLM {
    
    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApikey;

    private final WebClient webClient;

    public FachadaLLM(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String chat(String sportName, String weekAvailability) {

        String prompt = "Responda como um especialista em esportes. " +
                "Baseado no esporte: " + sportName + 
                ", gere uma rotina de treino personalizada para o usuário, " + 
                "especializado para o esporte desejado. " + 
                "A rotina deve preencher os seguintes dias e horários disponíveis: " +
               weekAvailability;


        Map<String, Object> requestBody = Map.of(
            "contents", new Object[] {
                Map.of("parts", new Object[] {
                    Map.of("text", prompt)
                })}
        );

        // Make Api call
        String response = webClient.post()
            .uri(geminiApiUrl + geminiApikey)
            .header("Content-Type", "application/json")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class)
            .block();

        // Return the response
        return response;
    }

    public String chat2(){
        String prompt = "Quantos anos tem o Neymar?";

        Map<String, Object> requestBody = Map.of(
            "contents", new Object[] {
                Map.of("parts", new Object[] {
                    Map.of("text", prompt)
                })}
        );

        // Make Api call
        String response = webClient.post()
            .uri(geminiApiUrl + geminiApikey)
            .header("Content-Type", "application/json")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class)
            .block();

        // Return the response
        return response;
    }
}
