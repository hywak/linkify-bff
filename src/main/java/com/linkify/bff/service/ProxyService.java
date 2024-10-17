package com.linkify.bff.service;

import com.linkify.bff.service.dto.ShortUrlDto;
import com.linkify.bff.web.controller.url.CreateShortUrlRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProxyService {
    private final WebClient webClient;

    public ProxyService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://external-service-url").build();
    }

    public Mono<ShortUrlDto> getShortUrl(String slug) {
        return webClient.get()
                .uri("http://localhost:8080/v1/urls/{slug}", slug)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(ShortUrlDto.class);
                    } else {
                        return Mono.error(new RuntimeException("Failed to create short URL: " + clientResponse.statusCode()));
                    }
                });
    }

    public Mono<ShortUrlDto> createShortUrl(CreateShortUrlRequest request) {
        return webClient.post()
                .uri("http://localhost:8080/v1/urls")
                .bodyValue(request)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return clientResponse.bodyToMono(ShortUrlDto.class);
                    } else {
                        return Mono.error(new RuntimeException("Failed to create short URL: " + clientResponse.statusCode()));
                    }
                });
    }
}
