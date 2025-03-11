package br.com.alc.ecommerce.checkout.infrastructure.cucumber.configurator;

import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.KafkaManager;
import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.PostgresManager;
import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.RedisManager;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Log4j2
@Component
@AllArgsConstructor
public class EnvironmentConfigurator {

    private static final List<String> TOPICS = asList("authorize-sale-topic", "sale-callback-topic");

    private final PostgresManager postgresManager;
    private final KafkaManager kafkaManager;
    private final RedisManager redisManager;
    private final WireMockServer wireMockServer;

    @SneakyThrows
    public void configureEnvironment() {
        log.info("START - Configure Environment");
        postgresManager.removeForeignKeys();
        postgresManager.cleanDatabase();
        postgresManager.resetSequences();
        kafkaManager.disableAllListeners();
        kafkaManager.clearTopics(TOPICS);
        redisManager.clearCache();
        wireMockServer.resetAll();
        log.info("END - Configure Environment");
    }
}