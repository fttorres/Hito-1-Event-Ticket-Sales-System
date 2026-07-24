package com.neonpulse.service;

import com.neonpulse.notification.MessageNotifier;

import java.math.BigDecimal;
import java.util.List;

/**
 * Servicio encargado de procesar el pago de una compra y notificar el
 * resultado a través de uno o varios canales ({@link MessageNotifier}).
 */
public class PaymentService {

    private final List<MessageNotifier> notifiers;

    /**
     * Crea el servicio de pagos con uno o varios notificadores.
     *
     * <p>Al recibir los notificadores como varargs, el servicio puede
     * trabajar con cero, uno o múltiples canales de notificación de forma
     * simultánea (por ejemplo, SMS y correo a la vez).</p>
     *
     * @param notifiers los canales de notificación a utilizar tras procesar el pago
     */
    public PaymentService(MessageNotifier... notifiers) {
        this.notifiers = List.of(notifiers);
    }

    /**
     * Procesa el pago de una compra.
     *
     * @param cardNumber número de tarjeta (o identificador de medio de pago) del cliente
     * @param amount     monto total a cobrar
     * @throws IllegalArgumentException si el número de tarjeta está vacío/nulo
     *                                   o si el monto es nulo o no es positivo
     */
    public void processPayment(String cardNumber, BigDecimal amount) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de tarjeta no puede estar vacío");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto a pagar debe ser mayor a cero");
        }

        String trimmedCard = cardNumber.trim();
        String last4Digits = trimmedCard.length() >= 4
                ? trimmedCard.substring(trimmedCard.length() - 4)
                : trimmedCard;

        String message = "Pago de $" + amount + " procesado con tarjeta terminada en " + last4Digits;

        for (MessageNotifier notifier : notifiers) {
            notifier.sendNotification(trimmedCard, message);
        }
    }
}
