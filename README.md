# NeonPulse - Sistema de Gestión y Validación de Compra de Entradas (Hito 1)

Este proyecto implementa el núcleo de lógica de negocio para la plataforma **NeonPulse**, encargada del procesamiento, validación de stock y cobro de entradas para eventos y festivales.

---

## 📌 Características Principales

- **Validación de Cantidad por Transacción**: Restricción de límites mínimos y máximos por compra mediante `PurchaseValidator`.
- **Verificación de Inventario**: Validación de disponibilidad en tiempo real mediante `StockManager`.
- **Cálculo Monetario de Alta Precisión**: Uso estricto de `BigDecimal` en `TicketItem` y `ShoppingCart` para prevenir errores de redondeo financiero.
- **Orquestación de Compras y Pagos**: Coordinación desacoplada en `PurchaseService` y abstracción de medios de notificación multicanal en `PaymentService` mediante `MessageNotifier`.
- **Suite de Pruebas Unitarias**: Pruebas parametrizadas y mocks estricto con JUnit 5, Mockito y AssertJ, alcanzando una cobertura de código superior al **90%**.

---

## 🛠️ Arquitectura y Patrones de Diseño

1. **Inyección de Dependencias (DI)**: `PurchaseService` y `PaymentService` reciben sus dependencias a través de constructores, facilitando el desacoplamiento y la prueba unitaria aislada.
2. **Patrón Estrategia (Strategy Pattern)**: La interfaz `MessageNotifier` permite intercalar distintos canales de notificación (`SmsNotifier`, email, etc.) de forma transparente.
3. **Colecciones Inmutables**: `ShoppingCart.getItems()` retorna vistas no modificables (`Collections.unmodifiableList`) para garantizar el principio de encapsulamiento.
4. **Manejo Explicito de Excepciones**: Excepciones de dominio personalizadas como `InvalidQuantityException` y `OutOfStockException`.

---

## 📁 Estructura del Proyecto

```text
Hito 1/
├── pom.xml                   # Configuración global de Maven, dependencias y plugins
├── .gitignore                # Reglas de exclusión de artefactos de compilación e IDE
├── README.md                 # Documentación técnica del proyecto
└── src/
    ├── main/java/com/neonpulse/
    │   ├── domain/           # Modelos de dominio y reglas de negocio (ShoppingCart, PurchaseValidator, StockManager, TicketItem)
    │   ├── exception/        # Excepciones de dominio (InvalidQuantityException, OutOfStockException)
    │   ├── notification/     # Interfaces e implementaciones de notificación (MessageNotifier, SmsNotifier)
    │   └── service/          # Servicios de aplicación y orquestación (PurchaseService, PaymentService)
    └── test/java/com/neonpulse/
        ├── domain/           # Pruebas unitarias de dominio (PurchaseValidatorTest, ShoppingCartTest, StockManagerTest)
        └── service/          # Pruebas unitarias de servicio y mocks (PaymentServiceTest, PurchaseServiceTest)
```

---

## 📋 Requisitos Previos

- **Java Development Kit (JDK)**: Versión 17 o superior.
- **Apache Maven**: Versión 3.8.0 o superior.

---

## 🚀 Compilación y Ejecución

### 1. Compilar el proyecto
```bash
mvn clean compile
```

### 2. Ejecutar la suite completa de pruebas unitarias
```bash
mvn clean test
```

### 3. Generar el reporte de cobertura de código (JaCoCo)
```bash
mvn jacoco:report
```
El reporte HTML interactivo estará disponible en `target/site/jacoco/index.html`.

---

## 🧪 Resumen de Pruebas Unitarias

| Clase de Prueba | Descripción / Cobertura |
| :--- | :--- |
| **`PurchaseValidatorTest`** | Pruebas parametrizadas (`@ParameterizedTest` / `@ValueSource`) para validar límites de entradas. |
| **`ShoppingCartTest`** | Verificación de total inicial, suma de subtotales e inmutabilidad de lista. |
| **`StockManagerTest`** | Verificación de disponibilidad suficiente y lanzamiento de `OutOfStockException`. |
| **`PaymentServiceTest`** | Verificación de cobros, formato de tarjeta y notificación mediante mocks de Mockito. |
| **`PurchaseServiceTest`** | Prueba de integración orquestada comprobando interacción ordenada entre validadores, inventario y pasarela de pago. |
