package com.my_chat_gpt.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import reactor.core.publisher.Mono;

@Service
public class GeminiService {

    private final WebClient webClient;

    @Autowired
    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    private final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=%s";

    public String llamadaApi(String prompt, String apiKey) {
        String apiURL = String.format(API_URL_TEMPLATE, apiKey);

        // Indicamos que el dato que le estamos enviando es un JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        

        // Tratamiento del JSON con Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode partsNode = objectMapper.createObjectNode();
        ObjectNode contentNode = objectMapper.createObjectNode();
        ObjectNode requestBodyNode = objectMapper.createObjectNode();

        partsNode.put("text", prompt);
        contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));
        requestBodyNode.set("contents", objectMapper.createArrayNode().add(contentNode));

        String cuerpoPregunta;
        try {
            cuerpoPregunta = objectMapper.writeValueAsString(requestBodyNode);
            System.out.println(cuerpoPregunta);
        } catch (Exception e) {
            throw new RuntimeException("Fallamos en construir el JSON", e);
        }
   

        // Realizar la solicitud usando WebClient
        Mono<String> response = webClient.post()
                .uri(apiURL)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(cuerpoPregunta))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new RuntimeException("Error 4xx: " + errorBody)));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new RuntimeException("Error 5xx: " + errorBody)));
                })
                .bodyToMono(String.class);

        // Bloquear para obtener la respuesta (no recomendado en aplicaciones reactivas) 
        System.out.println(response.block());
      return response.block();
    }
}

