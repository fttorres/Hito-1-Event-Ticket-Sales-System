package com.neonpulse.service;

import com.neonpulse.notification.MessageNotifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class PaymentServiceTest {

    @Test
    @DisplayName("Debe notificar a través del canal correspondiente cuando el pago se procesa con éxito")
    void shouldNotifyWhenPaymentIsProcessedSuccessfully() {
        // Arrange (Preparar): se simula (mock) un MessageNotifier para no
        // depender de una implementación real (SMS, email, etc.)
        MessageNotifier mockNotifier = mock(MessageNotifier.class);
        PaymentService paymentService = new PaymentService(mockNotifier);

        // Act (Ejecutar)
        paymentService.processPayment("4111 1111 1111 1111", new BigDecimal("150.00"));

        // Assert (Verificar): se comprueba que sendNotification fue
        // efectivamente invocado sobre el mock, una única vez.
        verify(mockNotifier).sendNotification(anyString(), anyString());
    }

    @Test
    @DisplayName("Debe lanzar excepción al procesar un pago con número de tarjeta vacío y no notificar")
    void shouldFailWhenProcessingPaymentWithEmptyCardNumber() {
        // Arrange
        MessageNotifier mockNotifier = mock(MessageNotifier.class);
        PaymentService paymentService = new PaymentService(mockNotifier);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.processPayment("   ", new BigDecimal("150.00"));
        });

        // Al fallar la validación antes de notificar, el mock nunca debió
        // haber sido invocado.
        verify(mockNotifier, never()).sendNotification(anyString(), anyString());
    }

    @Test
    @DisplayName("Debe lanzar excepción al procesar un pago con monto igual o menor a cero")
    void shouldFailWhenProcessingPaymentWithNonPositiveAmount() {
        // Arrange
        MessageNotifier mockNotifier = mock(MessageNotifier.class);
        PaymentService paymentService = new PaymentService(mockNotifier);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.processPayment("4111111111111111", BigDecimal.ZERO);
        });

        verify(mockNotifier, never()).sendNotification(anyString(), anyString());
    }
}
