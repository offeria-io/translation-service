# Translation Service

**Offeria — a product by [Al‑Wahha Al‑Sehriya](https://github.com/Al-Wahha-Al-Sehriya).**

[Company website](https://wahasehriya.com/) · [Offeria repositories](https://github.com/offeria-io)

## Description
The Translation Service manages multi-language support for the Offeria platform. It stores and retrieves translations for various platform messages, material descriptions, and other user-facing content.

## Architecture Diagram
```mermaid
graph TD
    Client[Gateway / Other Services] -->|Get Translation| TS[Translation Service]
    TS -->|Store| DB[(PostgreSQL)]
    TS -->|Register| DS[Discovery Service]
```

## File Structure
```text
translation-service/
├── k8s/                  # Kubernetes manifests
├── src/
│   ├── main/
│   │   ├── java/offeria/translation_service/
│   │   │   ├── controller/  # REST endpoints for translations
│   │   │   ├── domain/      # Entities and DTOs
│   │   │   ├── exception/   # Custom exceptions
│   │   │   ├── mapper/      # Object mapping
│   │   │   ├── messaging/   # Common messaging logic
│   │   │   ├── repository/  # Data access layer
│   │   │   ├── service/     # Business logic
│   │   │   └── TranslationServiceApplication.java
│   │   └── resources/       # Configuration
│   └── test/                # Unit tests
├── Dockerfile           # Docker configuration
└── pom.xml              # Maven dependencies
```

## Technologies
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**

## Key Dependencies
- `spring-boot-starter-data-jpa`: Persistence for translations.
- `spring-cloud-starter-netflix-eureka-client`: Discovery client.

## Environment Variables
- `SPRING_PROFILES_ACTIVE`: Active profile.
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE`: Discovery Service URL.
- `DB_URL`: JDBC URL for PostgreSQL.
- `DB_USERNAME`: PostgreSQL username.
- `DB_PASSWORD`: PostgreSQL password.
