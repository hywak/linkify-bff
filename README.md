# Linkify BFF

A backend-for-frontend service for the link shortener application. Service is designed to return different responses
based on the user's device type (mobile, desktop, tablet).

> Due to time limitation service handles only happy path scenarios. No validation or error handling is implemented. As
> well as no tests are written.

## Performance assurance

Using Spring WebFlux for high-load scenarios allows for seamless asynchronous processing. By embracing a reactive,
non-blocking approach and maintaining a stateless design, the system optimizes resource utilization. This enables smooth
scaling under heavy traffic while ensuring a responsive experience for users.

## Pre-requisites

- Docker
- Java 23

## How to run

### Run the application

```bash
./gradlew bootRun 
```

> Make sure the linkify service is running on port 8080 before running the application.

## URL Shortener API Documentation

### Base URL

`/web/v1/urls`

### Endpoints

#### 1. Get Short URL by Slug

Fetches the original URL associated with the provided slug.

```bash
curl --location --request GET 'http://127.0.0.1:8081/web/v1/urls/O8qHML7SanU'
```

- **Parameters:**
    - `slug` (path) - The unique slug for the short URL.

#### 2. Create Short URL

Creates a short URL for the provided original URL.

```bash
curl --location 'http://127.0.0.1:8081/web/v1/urls' \
--header 'Content-Type: application/json' \
--data '{
    "originalUrl": "http://google3.com",
    "owner": "Michal",
    "expirationDate": "2024-09-28T10:00:00+02:00"
}'
```

