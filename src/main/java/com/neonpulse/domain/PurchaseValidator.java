package com.neonpulse.domain;
import com.neonpulse.exception.InvalidQuantityException;

 /* 
 Cantidad de entradas solicitadas en una compra
 debe cumplir con  el maximo permitido por transaccion
 */

 public class PurchaseValidator {

    /* 
    Comprueba que el numero de entradas no exceda el maximo permitido por transaccion
    */

    public static final int MAX_QUANTITY_PER_PURCHASE = 10;

    /* 
    Cantidad de entradas solicitadas en una compra
     */
public void processQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidQuantityException("La cantidad debe ser mayor a 0: " + quantity);
        }

      if (quantity > MAX_QUANTITY_PER_PURCHASE) {
            throw new InvalidQuantityException( "La cantidad solicitada (" + quantity + ") supera el máximo permitido por compra ("
                            + MAX_QUANTITY_PER_PURCHASE + ")");
        }
}
}

