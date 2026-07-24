package com.neonpulse.domain;

import com.neonpulse.exception.OutOfStockException;

/**
 * Contiene la regla de negocio encargada de validar el inventario
 */
public class StockManager {

    /**
     * Verifica que exista inventario suficiente para cubrir la cantidad solicitada.
     * @param stock    cantidad de entradas actualmente disponibles
     * @param quantity cantidad de entradas solicitadas por el comprador
     * @throws OutOfStockException si {@code stock < quantity}
     */
    public void checkAvailability(int stock, int quantity) {
        if (stock < quantity) {
            throw new OutOfStockException(
                    "Stock insuficiente para completar la compra. Disponible: "
                            + stock + ", Solicitado: " + quantity);
        }
    }
}
