package com.neonpulse.exception;


/*
Excepcion que se lanza cuando la cantidad de entradas solicitadas en una compra
supera el maximo permitido por transaccion
*/
public class OutOfStockException extends RuntimeException {


    /*Constructor de la excepcion*/
    public OutOfStockException(String message) {
        super(message);
    }
}