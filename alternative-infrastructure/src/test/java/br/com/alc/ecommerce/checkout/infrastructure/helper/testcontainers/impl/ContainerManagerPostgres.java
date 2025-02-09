package br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import static java.util.Collections.singletonList;

public class ContainerManagerPostgres extends AbstractContainerManager {

    private static final int POSTGRES_PORT = 5432;

    @Override
    protected GenericContainer createContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15-alpine")
                .withDatabaseName("ecommerce_db")
                .withUsername("ecommerce_user")
                .withPassword("ecommerce_user")
                .withReuse(false);

        container.setPortBindings(singletonList(POSTGRES_PORT + ":" + POSTGRES_PORT));
        container.withExposedPorts(POSTGRES_PORT);
        return container;
    }

    @Override
    protected String createContainerName() {
        return "PostgreSQL";
    }
}