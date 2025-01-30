package br.com.alc.ecommerce.checkout.infrastructure.testcontainers.factory;

import br.com.alc.ecommerce.checkout.infrastructure.testcontainers.ContainerManager;
import br.com.alc.ecommerce.checkout.infrastructure.testcontainers.impl.ContainerManagerOracle;
import br.com.alc.ecommerce.checkout.infrastructure.testcontainers.impl.ContainerManagerRabbitMQ;
import br.com.alc.ecommerce.checkout.infrastructure.testcontainers.impl.ContainerManagerRedis;

import java.util.List;

public interface ContainerFactory {

    ContainerManagerRedis getRedisInstance();

    ContainerManagerOracle getOracleInstance();

    ContainerManagerRabbitMQ getRabbitInstance();

    List<ContainerManager> getInstances();

}