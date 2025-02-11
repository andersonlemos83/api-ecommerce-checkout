package br.com.alc.ecommerce.checkout.infrastructure.helper.manager;

public interface PostgresManager {

    void removeForeignKeys();

    void cleanDatabase();

    void resetSequences();

}