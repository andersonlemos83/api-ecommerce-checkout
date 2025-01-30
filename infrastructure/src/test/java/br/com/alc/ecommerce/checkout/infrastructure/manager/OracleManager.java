package br.com.alc.ecommerce.checkout.infrastructure.manager;

public interface OracleManager {

    void removeForeignKeys();

    void cleanDatabase();

    void resetSequences();

}