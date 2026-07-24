package com.neonpulse.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Carrito de compras  ({@link TicketItem})
 */
public class ShoppingCart {

    private final List<TicketItem> items = new ArrayList<>();

    /**
     * Agrega una entrada al carrito.
     *
     * @param item la línea (evento, precio unitario y cantidad) a agregar
     */
    public void addItem(TicketItem item) {
        items.add(item);
    }

    /**
     * @return una vista de  entradas actualmente en el carrito
     */
    public List<TicketItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Calcula el total a pagar sumando el subtotal de cada línea del carrito.
     *
     * <p>El cálculo se realiza con {@link BigDecimal} para garantizar
     * precisión monetaria exacta, evitando los errores de redondeo que
     * introduciría el uso de {@code double} en operaciones financieras.</p>
     *
     * @return el total del carrito; {@link BigDecimal#ZERO} si está vacío
     */
    public BigDecimal getTotal() {
        return items.stream()
                .map(TicketItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
