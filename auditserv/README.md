# 🧾 auditserv

The audit trail of the **Notification Platform** — intended to consume lifecycle events emitted by every other service and persist them for traceability, debugging, and compliance.

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

- 📥 Consume events from the Kafka `notification.audit` topic, published by [`notificationapi`](../notificationapi/README.md), [`processor`](../processor/README.md), and [`channel.worker`](../channel.worker/README.md).
- 🍃 Persist a durable audit/delivery history in MongoDB, keyed by tenant and request/correlation id.
- 🔍 Expose a queryable trail for debugging "what happened to notification X" across the pipeline.

---

## 🚧 Current Status

This service is currently a **scaffolded Spring Boot application** — dependencies and build are wired, but no consumers, persistence model, or querying has been implemented yet. Only the bootstrap `AuditservApplication` main class exists today.

---

## 🧰 Tech Stack

| Concern | Technology |
|---|---|
| ☕ Language | Java 25 |
| 🍃 Framework | Spring Boot `4.1.2` (standalone — not yet wired to `notification-core`) |
| 🧵 Messaging | Spring Kafka |
| 🍃 Database | MongoDB |
| 📊 Ops | Spring Boot Actuator |
| 🧪 Testing | Spring Boot test starters (Actuator, Kafka, MongoDB) |

---

## ⚙️ Configuration

`src/main/resources/application.yaml` currently only sets the application name:

```yaml
spring:
  application:
    name: auditserv
```

Kafka consumer group settings and the MongoDB connection URI are expected to be added here as audit-persistence logic is implemented (see [`notificationapi`](../notificationapi/README.md#️-configuration) for the sibling conventions already in use).

---

## 🚀 Running Locally

```bash
docker compose up -d   # from repo root — mongo, kafka

cd auditserv
./mvnw spring-boot:run
```

> ℹ️ No `server.port` is configured, so this defaults to port `8080` — override it if running alongside other services on the same machine. This service also does not yet declare a web starter, so no HTTP endpoints are exposed.

---

## 📁 Package Structure

```
auditserv/src/main/java/com/notification/auditserv/
└── AuditservApplication.java   # Spring Boot entry point (scaffold only)
```
