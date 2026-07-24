package com.neonpulse.service;

import com.neonpulse.domain.PurchaseValidator;
import com.neonpulse.domain.ShoppingCart;
import com.neonpulse.domain.StockManager;
import com.neonpulse.domain.TicketItem;

import java.math.BigDecimal;

/**
 * Orquesta el proceso completo de compra de entradas: valida la cantidad
 * solicitada, verifica el stock disponible y, si todo es correcto, delega
 * el cobro en {@link PaymentService}.
 */
public class PurchaseService {

    private final StockManager stockManager;
    private final PurchaseValidator purchaseValidator;
    private final PaymentService paymentService;

    public PurchaseService(StockManager stockManager,
                            PurchaseValidator purchaseValidator,
                            PaymentService paymentService) {
        this.stockManager = stockManager;
        this.purchaseValidator = purchaseValidator;
        this.paymentService = paymentService;
    }

    /**
     * Procesa la compra completa de un carrito.
     *
     * @param cart            el carrito con las entradas a comprar
     * @param availableStock  el stock disponible para validar contra la cantidad total solicitada
     * @param cardNumber      el medio de pago del cliente
     */
    public void processPurchase(ShoppingCart cart, int availableStock, String cardNumber) {
        int totalQuantity = cart.getItems().stream()
                .mapToInt(TicketItem::getQuantity)
                .sum();

        purchaseValidator.processQuantity(totalQuantity);
        stockManager.checkAvailability(availableStock, totalQuantity);

        BigDecimal total = cart.getTotal();
        paymentService.processPayment(cardNumber, total);
    }
}
