package com.neonpulse.service;

import com.neonpulse.domain.PurchaseValidator;
import com.neonpulse.domain.ShoppingCart;
import com.neonpulse.domain.StockManager;
import com.neonpulse.domain.TicketItem;
import com.neonpulse.exception.InvalidQuantityException;
import com.neonpulse.exception.OutOfStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class PurchaseServiceTest {

    private StockManager stockManager;
    private PurchaseValidator purchaseValidator;
    private PaymentService paymentService;
    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        stockManager = mock(StockManager.class);
        purchaseValidator = mock(PurchaseValidator.class);
        paymentService = mock(PaymentService.class);
        purchaseService = new PurchaseService(stockManager, purchaseValidator, paymentService);
    }

    @Test
    @DisplayName("Debe procesar la compra exitosamente cuando la cantidad y el stock son válidos")
    void shouldProcessPurchaseSuccessfullyWhenInputsAreValid() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new TicketItem("Entrada VIP", new BigDecimal("50.00"), 2));
        int availableStock = 10;
        String cardNumber = "1234567812345678";

        // Act
        purchaseService.processPurchase(cart, availableStock, cardNumber);

        // Assert
        verify(purchaseValidator).processQuantity(2);
        verify(stockManager).checkAvailability(availableStock, 2);
        verify(paymentService).processPayment(cardNumber, new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("Debe lanzar InvalidQuantityException y no verificar stock ni procesar pago si la cantidad es inválida")
    void shouldFailWhenQuantityIsInvalid() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new TicketItem("Entrada General", new BigDecimal("30.00"), 15)); // Excede máximo
        int availableStock = 20;
        String cardNumber = "1234567812345678";

        doThrow(new InvalidQuantityException("Cantidad inválida"))
                .when(purchaseValidator).processQuantity(15);

        // Act & Assert
        assertThrows(InvalidQuantityException.class, () ->
                purchaseService.processPurchase(cart, availableStock, cardNumber)
        );

        verify(stockManager, never()).checkAvailability(anyInt(), anyInt());
        verify(paymentService, never()).processPayment(any(), any());
    }

    @Test
    @DisplayName("Debe lanzar OutOfStockException y no procesar pago si el stock es insuficiente")
    void shouldFailWhenStockIsInsufficient() {
        // Arrange
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new TicketItem("Entrada Preferencial", new BigDecimal("40.00"), 5));
        int availableStock = 2; // Stock insuficiente para 5
        String cardNumber = "1234567812345678";

        doThrow(new OutOfStockException("Stock insuficiente"))
                .when(stockManager).checkAvailability(availableStock, 5);

        // Act & Assert
        assertThrows(OutOfStockException.class, () ->
                purchaseService.processPurchase(cart, availableStock, cardNumber)
        );

        verify(purchaseValidator).processQuantity(5);
        verify(paymentService, never()).processPayment(any(), any());
    }
}
