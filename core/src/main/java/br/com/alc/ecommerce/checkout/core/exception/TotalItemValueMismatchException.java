package br.com.alc.ecommerce.checkout.core.exception;

public final class TotalItemValueMismatchException extends RuntimeException {

    public TotalItemValueMismatchException() {
        super("O valor total dos itens está diferente do total informado.");
    }
}