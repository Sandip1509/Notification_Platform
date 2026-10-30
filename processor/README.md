# ⚙️ processor

The consumer service that will sit between `notificationapi` and `channel.worker`: picking up queued notifications from Kafka, resolving/rendering them, and routing them onward for delivery.

⬅️ Back to [platform overview](../README.md)

---

## 🗂️ Table of Contents

- [📣 Intended Responsibilities](#-intended-responsibilities)
- [🚧 Current Status](#-current-status)
- [🧰 Tech Stack](#-tech-stack)
- [⚙️ Configuration](#️-configuration)
- [🚀 Running Locally](#-running-locally)
- [📁 Package Structure](#-package-structure)

---

## 📣 Intended Responsibilities

Based on its position in the [platform architecture](../README.md#️-architecture):

- 📥 Consume notification events from the Kafka `notification.ingest` topic (published by [`notificationapi`](../notificationapi/README.md)).
- 🧩 Resolve/render the notification payload (template + dynamic variables).
- 🧮 Use Redis for caching during processing (e.g. de-duplication, rate-limiting, template lookups).
- 📤 Hand the rendered message off to [`channel.worker`](../channel.worker/README.md) for actual delivery.
- 🧾 Emit progress/failure events to the `notification.audit` topic for [`auditserv`](../auditserv/README.md).

---

## 🚧 Current Status

This service is currently a **scaffolded Spring Boot application** — dependencies and build are wired, but no business logic (consumers, services, controllers) has been implemented yet. Only the bootstrap `ProcessorApplication` main class exists today.

---

## 🧰 Tech Stack

| Concern | Technology |
|---|---|
| ☕ Language | Java 25 |
| 🍃 Framework | Spring Boot `4.1.2` (standalone — not yet wired to `notification-core`) |
| 🌐 Web | Spring WebMVC |
| 🧵 Messaging | Spring Kafka |
| 🍃 Database | MongoDB |
| 🧮 Cache | Redis |
| 📊 Ops | Spring Boot Actuator |
| 🧱 Boilerplate | Lombok |
| 🧪 Testing | Spring Boot test starters (Actuator, Redis, Kafka, MongoDB, WebMVC) |

---

## ⚙️ Configuration

`src/main/resources/application.yaml` currently only sets the application name:

```yaml
spring:
  application:
    name: processor
```

Kafka topics, MongoDB URI, and Redis connection details are expected to be added here as the consumer logic is implemented (see [`notificationapi`](../notificationapi/README.md#️-configuration) for the sibling conventions already in use).

---

## 🚀 Running Locally

```bash
docker compose up -d   # from repo root — redis, mongo, kafka

cd processor
./mvnw spring-boot:run
```

> ℹ️ No `server.port` is configured, so this defaults to port `8080` — override it if running alongside other services on the same machine.

---

## 📁 Package Structure

```
processor/src/main/java/com/notification/processor/
└── ProcessorApplication.java   # Spring Boot entry point (scaffold only)
```
