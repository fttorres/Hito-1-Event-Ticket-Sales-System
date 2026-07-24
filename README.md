# Event Ticket Sales System (Hito 1)

> **Milestone 1** — Desafío Latam, Java Course, Globant Talento Ready.
> Practice exercise.

Ticket sales system for events, built with **Java 25 (LTS)** and **Maven**. It models the full purchase flow: quantity validation, stock verification, monetary-precise total calculation (`BigDecimal`), and payment processing with customer notification.

## Architecture

The project implements a **Pure Entity Core**, following **Clean Architecture** principles (Ports and Adapters):

| Layer | Package | Responsibility |
|---|---|---|
| **Domain (Core)** | `com.neonpulse.domain` | Pure entities and business rules: `ShoppingCart`, `TicketItem`, `StockManager`, `PurchaseValidator`. They depend on no framework, external library, or infrastructure detail. |
| **Domain exceptions** | `com.neonpulse.exception` | `OutOfStockException` and `InvalidQuantityException`: unchecked exceptions representing business conditions, not technical errors. |
| **Ports** | `com.neonpulse.notification.MessageNotifier` | Interface (port) used by the domain/services to notify, without knowing the actual channel. |
| **Adapters** | `com.neonpulse.notification.{SmsNotifier, DummyNotifier}` | Concrete implementations of the `MessageNotifier` port, interchangeable without touching business logic. |
| **Application services** | `com.neonpulse.service` | `PaymentService` and `PurchaseService` orchestrate the domain entities and ports to execute the complete use cases. |

This separation allows the **business core to be tested in isolation** (without infrastructure mocks) and notification adapters (SMS, email, console, etc.) to be added or replaced without modifying the business rules.

## Requirements

- JDK 25 (LTS)
- Maven 3.9+

## Execution Commands

From the project root (where `pom.xml` is located):

```bash
# Compile the project
mvn compile

# Run the full unit test suite (JUnit 5 + Mockito)
mvn test

# Package the final artifact (JAR)
mvn package
```

## Project Structure

```
neonpulse
├── pom.xml
└── src
    ├── main/java/com/neonpulse
    │   ├── domain
    │   │   ├── ShoppingCart.java
    │   │   ├── TicketItem.java
    │   │   ├── PurchaseValidator.java
    │   │   └── StockManager.java
    │   ├── exception
    │   │   ├── OutOfStockException.java
    │   │   └── InvalidQuantityException.java
    │   ├── notification
    │   │   ├── MessageNotifier.java
    │   │   ├── SmsNotifier.java
    │   │   └── DummyNotifier.java
    │   └── service
    │       ├── PaymentService.java
    │       └── PurchaseService.java
    └── test/java/com/neonpulse
        ├── domain
        │   ├── StockManagerTest.java
        │   ├── PurchaseValidatorTest.java
        │   └── ShoppingCartTest.java
        └── service
            └── PaymentServiceTest.java
```

