package br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.factory.impl;

import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.ContainerManager;
import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.factory.ContainerFactory;
import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl.ContainerManagerRabbitMQ;
import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl.ContainerManagerRedis;

import java.util.Arrays;
import java.util.List;

public class ContainerFactoryImpl implements ContainerFactory {

    private static final ContainerManagerRedis containerManagerRedis;
    private static final ContainerManagerRabbitMQ containerManagerRabbitMQ;

    static {
        containerManagerRedis = new ContainerManagerRedis();
        containerManagerRabbitMQ = new ContainerManagerRabbitMQ();
    }

    @Override
    public ContainerManagerRedis getRedisInstance() {
        return containerManagerRedis;
    }

    @Override
    public ContainerManagerRabbitMQ getRabbitInstance() {
        return containerManagerRabbitMQ;
    }

    @Override
    public List<ContainerManager> getInstances() {
        return Arrays.asList(getRedisInstance(), getRabbitInstance());
    }
}