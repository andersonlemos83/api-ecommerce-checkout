package br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl;

import lombok.extern.log4j.Log4j2;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;

import static java.util.Arrays.asList;

@Log4j2
@SuppressWarnings("squid:S2925") // "Thread.sleep" should not be used in tests
public class ContainerManagerKafka extends AbstractContainerManager {

    private static final int SERVICE_PORT = 9093;

    @Override
    protected GenericContainer createContainer() {
        KafkaContainer container = new KafkaContainer("5.4.3");
        container.setPortBindings(asList(SERVICE_PORT + ":" + SERVICE_PORT));
        container.withExposedPorts(SERVICE_PORT);
        return container;
    }

    @Override
    protected String createContainerName() {
        return "Kafka";
    }
}