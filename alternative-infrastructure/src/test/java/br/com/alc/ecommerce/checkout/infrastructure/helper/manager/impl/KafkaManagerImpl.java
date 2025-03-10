package br.com.alc.ecommerce.checkout.infrastructure.helper.manager.impl;

import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.KafkaManager;
import br.com.alc.ecommerce.checkout.infrastructure.helper.util.ObjectMapperHelper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListOffsetsResult;
import org.apache.kafka.clients.admin.OffsetSpec;
import org.apache.kafka.clients.admin.RecordsToDelete;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.StreamSupport;

import static br.com.alc.ecommerce.checkout.infrastructure.helper.util.ObjectMapperHelper.generateJson;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.springframework.kafka.support.serializer.ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS;
import static org.springframework.kafka.support.serializer.ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS;
import static org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES;

@Log4j2
@Component
public class KafkaManagerImpl implements KafkaManager {

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final AdminClient adminClient;

    private final String bootstrapServers;

    public KafkaManagerImpl(KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry,
                            KafkaTemplate<String, Object> kafkaTemplate,
                            AdminClient adminClient,
                            @Value(value = "${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        this.kafkaListenerEndpointRegistry = kafkaListenerEndpointRegistry;
        this.kafkaTemplate = kafkaTemplate;
        this.adminClient = adminClient;
        this.bootstrapServers = bootstrapServers;
    }

    @Override
    public void disableAllListeners() {
        kafkaListenerEndpointRegistry.getAllListenerContainers().stream().forEach(MessageListenerContainer::stop);
    }

    @Override
    public void enableAllListeners() {
        kafkaListenerEndpointRegistry.getAllListenerContainers().stream().forEach(MessageListenerContainer::start);
    }

    @Override
    public void disableListener(String topicName) {
        kafkaListenerEndpointRegistry.getListenerContainers()
                .stream()
                .filter(listener -> asList(listener.getContainerProperties().getTopics()).contains(topicName))
                .forEach(MessageListenerContainer::stop);
    }

    @Override
    public void enableListener(String topicName) {
        kafkaListenerEndpointRegistry.getListenerContainers()
                .stream()
                .filter(listener -> asList(listener.getContainerProperties().getTopics()).contains(topicName))
                .forEach(MessageListenerContainer::start);
    }

    @Override
    public void clearTopics(List<String> topicNames) {
        topicNames.forEach(this::clearTopic);
    }

    @Override
    public void clearTopic(String topicName) {
        try {
            log.info("Purging topic: {}", topicName);
            List<TopicPartition> partitions = adminClient.describeTopics(singletonList(topicName))
                    .allTopicNames()
                    .get()
                    .get(topicName)
                    .partitions()
                    .stream()
                    .map(partitionInfo -> new TopicPartition(topicName, partitionInfo.partition()))
                    .toList();

            Map<TopicPartition, OffsetSpec> offsetSpecs = partitions.stream()
                    .collect(toMap(partition -> partition, partition -> OffsetSpec.latest()));

            Map<TopicPartition, ListOffsetsResult.ListOffsetsResultInfo> endOffsets = adminClient.listOffsets(offsetSpecs).all().get();

            Map<TopicPartition, RecordsToDelete> deleteRecords = endOffsets.entrySet().stream()
                    .collect(toMap(Map.Entry::getKey, entry -> RecordsToDelete.beforeOffset(entry.getValue().offset())));

            adminClient.deleteRecords(deleteRecords).all().get();
            log.info("Topic {} purged successfully", topicName);
        } catch (Exception exception) {
            log.error("Error purging topic {}: {}", topicName, exception.getMessage(), exception);
        }
    }

    @Override
    public List<String> getMessages(String topicName) {
        try (Consumer<String, Object> consumer = new KafkaConsumer<>(buildConsumeProperties())) {
            consumer.subscribe(singletonList(topicName));
            ConsumerRecords<String, Object> records = consumer.poll(Duration.ofSeconds(4));
            Iterable<ConsumerRecord<String, Object>> iterable = () -> records.records(topicName).iterator();
            return StreamSupport.stream(iterable.spliterator(), true)
                    .map(ConsumerRecord::value)
                    .map(ObjectMapperHelper::generateJson)
                    .toList();
        }
    }

    @Override
    public void sendMessage(String topic, Object request) {
        try {
            log.info("---> Sending to topic {} - {}", topic, generateJson(request));
            kafkaTemplate.send(topic, request);
        } catch (final Exception exception) {
            log.error("Error sendMessage: " + exception.getMessage(), exception);
        }
    }

    private Properties buildConsumeProperties() {
        Properties configs = new Properties();
        configs.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(GROUP_ID_CONFIG, "ecommerce-checkout-id-group");
        configs.put(KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configs.put(VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configs.put(KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        configs.put(VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        configs.put(TRUSTED_PACKAGES, "*");
        configs.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
        return configs;
    }
}