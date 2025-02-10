package br.com.alc.ecommerce.checkout.infrastructure.helper.manager.impl;

import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.KafkaManager;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Log4j2
@Component
@AllArgsConstructor
@SuppressWarnings("squid:S2925") // "Thread.sleep" should not be used in tests
public class KafkaManagerImpl implements KafkaManager {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Map<String, MessageListenerContainer> listenerContainers;

    @Override
    public void disableAllListeners() {
        listenerContainers.values().forEach(MessageListenerContainer::stop);
    }

    @Override
    public void enableAllListeners() {
        listenerContainers.values().forEach(MessageListenerContainer::start);
    }

    @Override
    public void disableListener(String topicName) {
        if (listenerContainers.containsKey(topicName)) {
            listenerContainers.get(topicName).stop();
        }
    }

    @Override
    public void enableListener(String topicName) {
        if (listenerContainers.containsKey(topicName)) {
            listenerContainers.get(topicName).start();
        }
    }

    @Override
    public void clearTopics(List<String> topicNames) {
        topicNames.forEach(this::clearTopic);
    }

    @Override
    public void clearTopic(String topicName) {
        kafkaTemplate.executeInTransaction(operations -> {
            operations.send(topicName, null);
            return null;
        });
    }

    @Override
    public List<String> getMessages(String topicName) {
        return List.of();
    }

    @Override
    public void sendMessage(String topic, Object request) {

    }

    @Override
    public Properties getTopicConfigurations(String topicName) {
        return null;
    }

    @Override
    public boolean topicExists(String topicName) {
        return false;
    }

    @Override
    public Integer getMessageCount(String topicName) {
        return 0;
    }
}