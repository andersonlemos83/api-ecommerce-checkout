package br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.factory;

import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.ContainerManager;
import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl.ContainerManagerOracle;
import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl.ContainerManagerRabbitMQ;
import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl.ContainerManagerRedis;

import java.util.List;

public interface ContainerFactory {

    ContainerManagerRedis getRedisInstance();

    ContainerManagerOracle getOracleInstance();

    ContainerManagerRabbitMQ getRabbitInstance();

    List<ContainerManager> getInstances();

}