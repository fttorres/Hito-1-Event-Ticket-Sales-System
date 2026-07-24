package com.neonpulse.notification;
/*
    Clase para simular el envío de notificaciones por SMS. 
    Para fines de prueba, la notificación no se envía realmente.
*/
public class SmsNotifier implements MessageNotifier {

    @Override
    public void sendNotification(String recipient, String message) {
        /*envía una notificación al usuario a través de SMS*/
        // Intencionalmente vacío: no realiza ningún envío real.
    }
}
