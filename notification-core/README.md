# 📚 notification-core

The shared parent POM and `common` library for the **Notification Platform**. It centralizes dependency/version management and reusable cross-cutting building blocks so individual microservices don't reinvent them.

⬅️ Back to [platform overview](../README.md)

> ⚠️ Not a microservice — this module produces no runnable application. It's a Maven **parent POM + shared library** currently consumed by [`notificationapi`](../notificationapi/README.md).

---

## 🗂️ Table of Contents

- [📣 What This Module Provides](#-what-this-module-provides)
- [🧰 Tech Stack](#-tech-stack)
- [📦 Dependency Management](#-dependency-management)
- [🏗️ Building](#️-building)
- [📁 Package Structure](#-package-structure)

---

## 📣 What This Module Provides

The `common` submodule ships reusable, auto-configured building blocks:

| Component | Purpose |
|---|---|
| 🆔 `CorrelationIdFilter` | Servlet filter that reads/generates `x-correlation-id`, puts it in `MDC` for log correlation, and echoes it on the response. Runs at `Ordered.HIGHEST_PRECEDENCE`. |
| 📦 `ApiResponse` / `GenericAPIResponse` | Standard response envelope wrapping payload + status code + request id. |
| 🧾 `ResponseHandler` | Convenience helper for controllers to build success/failure `GenericAPIResponse`s. |
| 🔢 `APIResponseCode` / `NotificationResponseCode` | Shared, platform-wide response/error codes implementing `ResponseCode`. |
| 🧰 `CommonUtils` | Shared utility helpers (e.g. null/empty checks). |
| ⚙️ `CommonAutoConfiguration` | Spring Boot `@AutoConfiguration` that wires up `ResponseHandler` and `CorrelationIdFilter` automatically for any service that depends on `common`. |

Because it's exposed as a Spring Boot auto-configuration (`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`), any microservice that adds `common` as a dependency gets correlation-id handling and the response envelope **for free** — no manual bean wiring required.

---

## 🧰 Tech Stack

| Concern | Technology |
|---|---|
| ☕ Language | Java 25 |
| 🍃 Framework | Spring Boot `4.1.2` (BOM via `spring-boot-dependencies`) |
| ☁️ Cloud BOM | Spring Cloud `2025.1.4` |
| 🌐 Web | Spring WebMVC |
| 📘 API Docs | springdoc-openapi-starter-webmvc-ui `2.8.14` |
| 🧱 Boilerplate | Lombok `1.18.42` |
| 🗄️ Test DB | H2 `2.3.232` (imported, available to consumers for tests) |
| 🔭 Observability (versions pinned here) | OpenTelemetry `2.22.0`, Micrometer `1.16.0` |
| 📦 Packaging | Google Jib `3.5.1` (version pinned for downstream modules) |

---

## 📦 Dependency Management

`notification-core/pom.xml` is a `pom`-packaged parent that centralizes versions for every downstream module via `<dependencyManagement>`:

- `spring-boot-dependencies` (Spring Boot BOM)
- `spring-cloud-dependencies` (Spring Cloud BOM)
- `lombok`, `h2`, `springdoc-openapi-starter-webmvc-ui`

Any service that sets `notification-core` as its Maven `<parent>` (currently only `notificationapi`) inherits these versions and can declare dependencies without repeating version numbers.

---

## 🏗️ Building

Install this module locally before building services that depend on it:

```bash
cd notification-core
mvn clean install
```

This builds both the parent POM and the `common` submodule and installs them to your local Maven repository (`~/.m2`).

---

## 📁 Package Structure

```
notification-core/
├── pom.xml                 # Parent POM — dependency/version management (BOM imports)
└── common/
    ├── pom.xml
    └── src/main/java/com/technologyzer/
        ├── config/          # CommonAutoConfiguration
        ├── constants/       # ApplicationConstant (e.g. correlation id header name)
        ├── filter/          # CorrelationIdFilter
        ├── models/          # ApiResponse, APIResponseCode, NotificationResponseCode, interfaces
        ├── services/        # ResponseHandler
        └── utils/           # CommonUtils
```
