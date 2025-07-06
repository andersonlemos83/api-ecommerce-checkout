package br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.factory.impl;

import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.ContainerManager;
import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.factory.ContainerFactory;
import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl.ContainerManagerPostgres;
import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl.ContainerManagerRedis;

import java.util.Arrays;
import java.util.List;

public class ContainerFactoryImpl implements ContainerFactory {

    private static final ContainerManagerRedis containerManagerRedis;
    private static final ContainerManagerPostgres containerManagerPostgres;

    static {
        containerManagerRedis = new ContainerManagerRedis();
        containerManagerPostgres = new ContainerManagerPostgres();
    }

    @Override
    public ContainerManagerRedis getRedisInstance() {
        return containerManagerRedis;
    }

    @Override
    public ContainerManagerPostgres getPostgresInstance() {
        return containerManagerPostgres;
    }

    @Override
    public List<ContainerManager> getInstances() {
        return Arrays.asList(getRedisInstance(), getPostgresInstance());
    }
}