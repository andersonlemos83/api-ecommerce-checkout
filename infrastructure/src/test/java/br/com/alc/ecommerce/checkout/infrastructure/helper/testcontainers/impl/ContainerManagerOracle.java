package br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.utility.MountableFile;

import static java.util.Collections.singletonList;

public class ContainerManagerOracle extends AbstractContainerManager {

    private static final int ORACLE_PORT = 1521;

    @Override
    protected GenericContainer createContainer() {
        OracleContainer container = new OracleContainer("gvenzl/oracle-xe:18.4.0-slim-faststart")
                .withPassword("oracle") // SYSTEM
                .withPrivilegedMode(true)
                .withReuse(false)
                .withCopyFileToContainer(MountableFile.forClasspathResource("schema.sql"), "/container-entrypoint-initdb.d/init.sql")
                .usingSid();
        container.setPortBindings(singletonList(ORACLE_PORT + ":" + ORACLE_PORT));
        container.withExposedPorts(ORACLE_PORT);
        return container;
    }

    @Override
    protected String createContainerName() {
        return "Oracle";
    }
}