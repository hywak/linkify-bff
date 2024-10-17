package com.linkify.bff.web.controller.url;

import com.linkify.bff.service.ProxyService;
import com.linkify.bff.service.dto.ShortUrlDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/web/v1/urls")
public class UrlShortenerController {
    private final ProxyService proxyService;

    @Autowired
    public UrlShortenerController(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @GetMapping("/{slug}")
    public Mono<ShortUrlResponse> getShortUrl(@PathVariable String slug) {
        return proxyService.getShortUrl(slug)
                .map(response -> new ShortUrlResponse(response.slug(),
                        response.redirectionUrl()));
    }

    @PostMapping
    public Mono<ResponseEntity<ShortUrlResponse>> createShortUrl(@RequestBody CreateShortUrlRequest request) {
        return proxyService.createShortUrl(request)
                .map(response -> {
                    if (response instanceof ShortUrlDto shortUrlDto) {
                        ShortUrlResponse shortUrlResponse = new ShortUrlResponse(
                                shortUrlDto.slug(),
                                shortUrlDto.redirectionUrl()
                        );
                        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrlResponse);
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                });
    }
}
