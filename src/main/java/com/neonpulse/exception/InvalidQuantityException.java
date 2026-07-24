package com.neonpulse.exception;


/*
Excepcion que se lanza cuando la cantidad de entradas solicitadas en una compra
supera el maximo permitido por transaccion
*/
public class InvalidQuantityException extends RuntimeException {


    /*Constructor de la excepcion*/

    public InvalidQuantityException(String message) {
        super(message);
    }
}