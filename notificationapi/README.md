# 🌐 notificationapi

The public-facing REST API of the **Notification Platform**. It owns notification **templates** and is the single entry point clients call to send a notification. It validates the request, resolves the template, and hands the job off asynchronously via Kafka for downstream processing and delivery.

⬅️ Back to [platform overview](../README.md)

---

## 🗂️ Table of Contents

- [📣 Responsibilities](#-responsibilities)
- [🧰 Tech Stack](#-tech-stack)
- [🔌 API Endpoints](#-api-endpoints)
- [🏢 Required Headers](#-required-headers)
- [📨 Publishing Strategy](#-publishing-strategy)
- [⚙️ Configuration](#️-configuration)
- [🚀 Running Locally](#-running-locally)
- [📁 Package Structure](#-package-structure)

---

## 📣 Responsibilities

- 📝 **Template management** — create, update, delete, and paginated-filter notification templates (`/api/template`).
- 📬 **Notification submission** — accept a `sendNotification` request, validate it against its template's dynamic variables, and publish it to Kafka for async processing (`/api/notifications`).
- 🧮 **Caching** — templates are cached per-tenant in Redis to reduce MongoDB load.
- 🔐 **Tenant isolation** — every request is scoped by an `x-tenant-id` header via `NotificationAuthFilter` + `NotificationContextHolder`.
- 🩺 **Startup health checks** — `ConnectionValidator` pings Kafka, MongoDB, and Redis on boot and fails fast if any are unreachable.

---

## 🧰 Tech Stack

| Concern | Technology |
|---|---|
| ☕ Language | Java 25 |
| 🍃 Framework | Spring Boot (via `notification-core` parent, 4.1.2) |
| 🌐 Web | Spring WebMVC |
| 🍃 Database | MongoDB (`spring-boot-starter-data-mongodb`) |
| 🧮 Cache | Redis (`spring-boot-starter-data-redis`) |
| 🧵 Messaging | Spring Kafka (`spring-boot-starter-kafka`) |
| ✅ Validation | `spring-boot-starter-validation` (Jakarta Bean Validation) |
| 🧾 Serialization | `jackson-databind` |
| 🧱 Boilerplate | Lombok |
| 📊 Ops | Spring Boot Actuator |
| 🔭 Tracing | OpenTelemetry Java agent (attached at runtime) |
| 📈 Metrics | Micrometer + Prometheus registry |
| 📦 Shared code | `com.technologyzer:common` (from [`notification-core`](../notification-core/README.md)) |
| 🐳 Packaging | Google Jib → `sandip2709/api:<tag>` |

---

## 🔌 API Endpoints

### 📬 Notifications

**`POST /api/notifications`** — submit a notification for async delivery.

```json
{
  "templateId": "b1a2c3d4-...",
  "notificationType": "EMAIL",
  "dynamicVariables": {
    "userName": "Sandip",
    "otp": "483920"
  }
}
```

`dynamicVariables` must exactly match the keys defined on the referenced template; mismatches are rejected with a validation error before anything reaches Kafka.

### 📝 Templates

**`POST /api/template`** — create a template.

```json
{
  "name": "otp-email",
  "messageTemplate": "Hi {{userName}}, your OTP is {{otp}}",
  "templateVariables": {
    "userName": "userName",
    "otp": "otp"
  }
}
```

**`GET /api/template`** — paginated/filterable template search (query params from `TemplateFilterRequest`, e.g. `name`).

**`PATCH /api/template/{id}`** — partial update of `name`, `messageTemplate`, and/or `templateVariables`.

**`DELETE /api/template/{id}`** — delete a template by id.

> All responses are wrapped in the shared `GenericAPIResponse<T>` envelope from `notification-core`.

---

## 🏢 Required Headers

| Header | Required on | Purpose |
|---|---|---|
| `x-tenant-id` | every `/api/**` call | Scopes templates, cache keys, and notifications to a tenant. Missing → `401 Unauthorized`. |
| `x-correlation-id` | optional | Propagated for tracing; auto-generated if omitted and echoed back on the response. |

---

## 📨 Publishing Strategy

Outbound messages go through a **generic publisher** (`GenericPublisherImpl`) that tries every enabled *primary* provider, then falls back to the first working *fallback* provider if none succeed:

| Role | Provider | Status |
|---|---|---|
| Primary | Kafka | ✅ Implemented |
| Primary | AWS SQS | 🚧 Stubbed (`messaging.providers.aws.enabled`) |
| Fallback | RabbitMQ | 🚧 Stubbed (`messaging.fallback.rabbitmq.enabled`) |
| Fallback | MQTT | 🚧 Stubbed (`messaging.fallback.mqtt.enabled`) |

Providers are toggled purely via configuration (`@ConditionalOnProperty`) — no code changes needed to enable/disable one.

---

## ⚙️ Configuration

Key properties from `src/main/resources/application.yaml`:

```yaml
spring:
  application:
    name: api
  mongodb:
    uri: mongodb://root:root@localhost:27017/admin
    database: notification-service
  kafka:
    bootstrap-servers: localhost:9092,localhost:9093,localhost:9094
    ingest:
      topic: notification.ingest
    audit:
      topic: notification.audit

messaging:
  providers:
    kafka:
      enabled: true
    aws:
      enabled: false
  fallback:
    rabbitmq:
      enabled: true
    mqtt:
      enabled: false
```

---

## 🚀 Running Locally

```bash
# from repo root — build + install the shared common library first
cd notification-core && mvn clean install && cd ..

# start infra: redis, mongo, kafka (see root README)
docker compose up -d

# run the API
cd notificationapi
./mvnw spring-boot:run
```

---

## 📁 Package Structure

```
notificationapi/src/main/java/com/technologyzer/notificationapi/
├── bootstrap/        # ConnectionValidator — startup health checks (Kafka/Mongo/Redis)
├── config/           # Mongo, Redis, and application property beans
├── controller/       # NotificationController, TemplateController
├── dao/              # TemplateDao + CacheService (Redis-backed)
├── exception/        # Custom exceptions + GlobalExceptionHandler
├── filters/           # NotificationAuthFilter (tenant header enforcement)
├── models/           # request / response / entity / context DTOs
├── pubsub/           # Kafka / AWS SQS / RabbitMQ / MQTT publisher strategy
├── service/           # NotificationService, TemplateService
└── utils/            # Shared helpers
```
