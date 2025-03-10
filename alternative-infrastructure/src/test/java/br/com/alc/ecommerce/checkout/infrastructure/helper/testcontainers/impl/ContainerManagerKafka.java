package br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl;

import lombok.extern.log4j.Log4j2;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.kafka.ConfluentKafkaContainer;

import static java.util.Arrays.asList;
import static org.testcontainers.utility.DockerImageName.parse;

@Log4j2
public class ContainerManagerKafka extends AbstractContainerManager {

    private static final int SERVICE_PORT = 9092;

    @Override
    protected GenericContainer createContainer() {
        ConfluentKafkaContainer container = new ConfluentKafkaContainer(parse("confluentinc/cp-kafka:7.6.0"));
        container.setPortBindings(asList(SERVICE_PORT + ":" + SERVICE_PORT));
        container.withExposedPorts(SERVICE_PORT);
        return container;
    }

    @Override
    protected String createContainerName() {
        return "Kafka";
    }
}