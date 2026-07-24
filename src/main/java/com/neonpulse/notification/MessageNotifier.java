package com.neonpulse.notification;

/*
 * Interface que define el comportamiento de los objetos encargados de entregar 
 * las notificaciones al usuario. 
 */     
public interface MessageNotifier {

    /*
        Recibe como parametro  el destinatario y el mensaje a enviar  
    */  
    void sendNotification(String recipient, String message);
}   