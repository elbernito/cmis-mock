# CMIS Mock System

A fully-featured, CMIS 1.2-compliant mock system based on Spring Boot 3.

## Overview

The **CMIS Mock System** simulates a complete CMIS 1.2-compliant backend using standard Spring Boot technologies.  
It includes extended support for core, optional, and advanced CMIS operations, along with a user-friendly web UI, monitoring, and in-memory persistence.

---

## Features

- ✅ Full CMIS 1.2 Support (Core, Optional & Advanced Features)
- ✅ OnePage Thymeleaf UI with Angular-Material Bootstrap Styling
- ✅ REST API with complete Swagger/OpenAPI documentation
- ✅ Integrated H2 In-Memory Database with Console Access
- ✅ File Upload to DB and Download from DB
- ✅ Full CRUD Support for:
    - Documents
    - Folders
    - Metadata
    - Versions
    - Policies
    - ACLs
    - Retention
    - Relationships
    - Objects
    - Type Definitions
- ✅ Spring Boot Actuator & Prometheus Metrics
- ✅ Exception Handling, Logging and JavaDoc for all components
- ✅ Clean Code principles, no dummy code or placeholders
- ✅ Test coverage via JUnit 5, MockMvc Integration Tests
- ✅ Dynamic Navigation with Anchor Links across all CMIS modules

---

## Usage

```bash
mvn spring-boot:run
```

Then open:

- 🖥️ Web UI: [http://localhost:9000](http://localhost:9000)
- 📄 Swagger UI: [http://localhost:9000/swagger-ui.html](http://localhost:9000/swagger-ui.html)
- 🧪 H2 Console: [http://localhost:9000/h2-console](http://localhost:9000/h2-console)  
  JDBC URL: `jdbc:h2:mem:cmisdb`
- 📈 Actuator Info: [http://localhost:9000/actuator](http://localhost:9000/actuator)
- 📊 Prometheus Metrics: [http://localhost:9000/actuator/prometheus](http://localhost:9000/actuator/prometheus)

---

## Example Endpoints

| HTTP | Path | Description |
|------|------|-------------|
| GET  | `/api/documents`       | List all documents |
| POST | `/api/documents`       | Create a new document |
| GET  | `/api/folders/{id}`    | Get folder details |
| GET  | `/api/acl/{objectId}`  | Get ACL for object |
| POST | `/api/policy/apply`    | Apply a policy |
| ...  | *(100+ other endpoints)* | Full CMIS 1.2 API coverage |

Use Swagger UI for full details and try-it-out features.

---

## Monitoring

- Prometheus: All services and endpoints export metrics automatically
- Example metrics:
    - `cmis.document.create.time`
    - `cmis.folder.get.time`
    - `cmis.acl.apply.time`

---

## Known Limitations

### 📌 ChangeLog Handling
- ✅ [fixed]ChangeLog entries are not persisted automatically.
- To enable it, inject `ChangeLogService` in service implementations and call it on Create/Update/Delete actions.

- It's not fully tested at this time

### ⚠️ Response Codes
- Some exception mappers may return generic `500 Internal Server Error` instead of `404 Not Found` or `400 Bad Request`.
- This will be improved in a future release.

---

## Disclaimer

> **Use at your own risk!**  
> This project is provided "as-is" for demonstration and testing purposes only.  
> No guarantees are made regarding correctness, reliability, or fitness for production use.

---

## License

MIT License  
© 2025 – ch.elbernito.cmis.mock  
See `LICENSE.md` for details.
