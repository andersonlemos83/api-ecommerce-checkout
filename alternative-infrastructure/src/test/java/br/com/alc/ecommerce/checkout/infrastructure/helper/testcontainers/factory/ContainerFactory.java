package br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.factory;

import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.ContainerManager;
import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl.ContainerManagerKafka;
import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl.ContainerManagerPostgres;
import br.com.alc.ecommerce.checkout.infrastructure.helper.testcontainers.impl.ContainerManagerRedis;

import java.util.List;

public interface ContainerFactory {

    ContainerManagerRedis getRedisInstance();

    ContainerManagerPostgres getPostgresInstance();

    ContainerManagerKafka getKafkaInstance();

    List<ContainerManager> getInstances();

}