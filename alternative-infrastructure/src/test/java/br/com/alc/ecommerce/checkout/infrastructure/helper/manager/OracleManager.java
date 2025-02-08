package br.com.alc.ecommerce.checkout.infrastructure.helper.manager;

public interface OracleManager {

    void removeForeignKeys();

    void cleanDatabase();

    void resetSequences();

}