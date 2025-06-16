# CMIS Mock System

A full CMIS 1.2 compliant mock implementation using Spring Boot 3, JPA, H2, Swagger, Actuator, Prometheus, and Thymeleaf.

## Features

- CMIS 1.2 Core, Optional & Advanced Features
- REST API with Swagger Documentation
- H2 In-Memory Database with Console Access
- Full CRUD for Documents (Folders, Metadata, etc. coming next)
- Prometheus Metrics and Spring Boot Actuator
- Custom Exception Handling

## Usage

- Start with `mvn spring-boot:run`
- Access [http://localhost:8080](http://localhost:8080) for UI
- Swagger UI: `/swagger-ui.html`
- H2 Console: `/h2-console` (JDBC: `jdbc:h2:mem:cmisdb`)
- Actuator: `/actuator`
- Prometheus: `/actuator/prometheus`

## Example API

- List Documents: `GET /api/documents`
- Create Document: `POST /api/documents`
- etc.

## License

MIT
