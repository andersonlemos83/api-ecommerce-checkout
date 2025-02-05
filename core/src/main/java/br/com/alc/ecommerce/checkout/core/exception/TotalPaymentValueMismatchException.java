package br.com.alc.ecommerce.checkout.core.exception;

public class TotalPaymentValueMismatchException extends RuntimeException {

    public TotalPaymentValueMismatchException() {
        super("O valor total dos pagamentos está diferente do total informado.");
    }
}