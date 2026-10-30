# 📨 channel.worker

The delivery edge of the **Notification Platform** — intended to dispatch a processed notification to its actual destination channel (SMS, Email, Webhook, etc.).

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

- 📥 Receive rendered notification jobs from [`processor`](../processor/README.md).
- 📡 Dispatch to the correct channel integration based on `NotificationType` (`SMS`, `EMAIL`, `WEBHOOK` — see the enum defined in [`notificationapi`](../notificationapi/README.md)).
- 🔁 Handle per-channel retries / delivery failures.
- 🧾 Emit delivery outcome events to the `notification.audit` topic for [`auditserv`](../auditserv/README.md).

---

## 🚧 Current Status

This service is currently a **scaffolded Spring Boot application** — dependencies and build are wired, but no channel integrations or consumers have been implemented yet. Only the bootstrap `Application` main class exists today.

---

## 🧰 Tech Stack

| Concern | Technology |
|---|---|
| ☕ Language | Java 25 |
| 🍃 Framework | Spring Boot `4.1.2` (standalone — not yet wired to `notification-core`) |
| 🌐 Web | Spring WebMVC |
| 🧵 Messaging | Spring Kafka |
| 📊 Ops | Spring Boot Actuator |
| 🧱 Boilerplate | Lombok |
| 🧪 Testing | Spring Boot test starters (Actuator, Kafka, WebMVC) |

---

## ⚙️ Configuration

`src/main/resources/application.yaml` currently only sets the application name:

```yaml
spring:
  application:
    name: channel.worker
```

Channel-provider credentials (SMS/Email/Webhook gateways) and Kafka consumer settings are expected to be added here as delivery logic is implemented.

---

## 🚀 Running Locally

```bash
docker compose up -d   # from repo root — kafka, etc.

cd channel.worker
./mvnw spring-boot:run
```

> ℹ️ No `server.port` is configured, so this defaults to port `8080` — override it if running alongside other services on the same machine.

---

## 📁 Package Structure

```
channel.worker/src/main/java/com/notification/channel/worker/
└── Application.java   # Spring Boot entry point (scaffold only)
```
