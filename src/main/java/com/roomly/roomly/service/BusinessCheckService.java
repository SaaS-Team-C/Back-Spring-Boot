package com.roomly.roomly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.roomly.roomly.dto.request.business.BusinessNumberCheckRequestDto;

import reactor.core.publisher.Mono;



@Service
// @RequiredArgsConstructor
public class BusinessCheckService {

    private final WebClient WebClient;
    
    @Value("${order.api.url}")
    private String oderUrl;
    @Value("${business-api.key}")
    private String serviceKey;

    

    
    public BusinessCheckService(WebClient.Builder webCBuilder){
        this.WebClient = webCBuilder.baseUrl(oderUrl).build();
    }
        public Mono<String> getBusinessInfo(BusinessNumberCheckRequestDto requestDto, String serviceKey){
        
        return WebClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/nts-businessman/v1/validate")
                .queryParam("serviceKey", serviceKey)
                .queryParam("returnType", "JSON")
                .build())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestDto)
            .retrieve()
            .bodyToMono(String.class);
    }
    
    
}
