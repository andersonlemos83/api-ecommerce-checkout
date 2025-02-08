package br.com.alc.ecommerce.checkout.infrastructure.cucumber.configurator;

import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.PostgresManager;
import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.RabbitMqManager;
import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.RedisManager;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Log4j2
@Component
@AllArgsConstructor
public class EnvironmentConfigurator {

    private static final List<String> QUEUES = asList("authorize-sale-queue", "sale-callback-queue");

    private final PostgresManager postgresManager;
    private final RabbitMqManager rabbitMqManager;
    private final RedisManager redisManager;
    private final WireMockServer wireMockServer;

    public void configureEnvironment() {
        log.info("START - Initializing Context");
        postgresManager.removeForeignKeys();
        postgresManager.cleanDatabase();
        postgresManager.resetSequences();
        rabbitMqManager.disableAllListeners();
        rabbitMqManager.clearQueues(QUEUES);
        redisManager.clearCache();
        wireMockServer.resetAll();
        log.info("END - Initializing Context");
    }
}