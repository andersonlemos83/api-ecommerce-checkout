package br.com.alc.ecommerce.checkout.infrastructure.helper.manager;

public interface H2Manager {

    void removeForeignKeys();

    void cleanDatabase();

    void resetSequences();

}