package com.neonpulse.domain;

import java.math.BigDecimal;
import java.util.Objects;   




public class TicketItem {

    private final String eventName;
    private final BigDecimal unitPrice;
    private final int quantity;

    public TicketItem(String eventName, BigDecimal unitPrice, int quantity) {
        this.eventName = Objects.requireNonNull(eventName, "El nombre del evento no puede ser nulo");
        this.unitPrice = Objects.requireNonNull(unitPrice, "El precio unitario no puede ser nulo");
        this.quantity = quantity;
    }

    public String getEventName() {
        return eventName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Calcula el subtotal de esta línea: precio unitario multiplicado por la cantidad.
     *
     * @return el subtotal como {@link BigDecimal}
     */
    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
