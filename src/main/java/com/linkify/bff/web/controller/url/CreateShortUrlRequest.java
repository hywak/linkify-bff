package com.linkify.bff.web.controller.url;

import java.time.OffsetDateTime;
import java.util.Optional;

public record CreateShortUrlRequest(
        String originalUrl,
        String owner,
        Optional<OffsetDateTime> expirationDate
) {
}
