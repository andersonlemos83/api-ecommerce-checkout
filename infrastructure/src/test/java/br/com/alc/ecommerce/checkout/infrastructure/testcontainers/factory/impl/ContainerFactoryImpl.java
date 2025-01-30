package br.com.alc.ecommerce.checkout.infrastructure.testcontainers.factory.impl;

import br.com.alc.ecommerce.checkout.infrastructure.testcontainers.ContainerManager;
import br.com.alc.ecommerce.checkout.infrastructure.testcontainers.factory.ContainerFactory;
import br.com.alc.ecommerce.checkout.infrastructure.testcontainers.impl.ContainerManagerOracle;
import br.com.alc.ecommerce.checkout.infrastructure.testcontainers.impl.ContainerManagerRabbitMQ;
import br.com.alc.ecommerce.checkout.infrastructure.testcontainers.impl.ContainerManagerRedis;

import java.util.Arrays;
import java.util.List;

public class ContainerFactoryImpl implements ContainerFactory {

    private static final ContainerManagerRedis containerManagerRedis;
    private static final ContainerManagerOracle containerManagerOracle;
    private static final ContainerManagerRabbitMQ containerManagerRabbitMQ;

    static {
        containerManagerRedis = new ContainerManagerRedis();
        containerManagerOracle = new ContainerManagerOracle();
        containerManagerRabbitMQ = new ContainerManagerRabbitMQ();
    }

    @Override
    public ContainerManagerRedis getRedisInstance() {
        return containerManagerRedis;
    }

    @Override
    public ContainerManagerOracle getOracleInstance() {
        return containerManagerOracle;
    }

    @Override
    public ContainerManagerRabbitMQ getRabbitInstance() {
        return containerManagerRabbitMQ;
    }

    @Override
    public List<ContainerManager> getInstances() {
        return Arrays.asList(getRedisInstance(), getOracleInstance(), getRabbitInstance());
    }
}