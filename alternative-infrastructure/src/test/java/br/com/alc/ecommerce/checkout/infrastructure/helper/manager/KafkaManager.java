package br.com.alc.ecommerce.checkout.infrastructure.helper.manager;

import java.util.List;

public interface KafkaManager {

    void disableAllListeners();

    void enableAllListeners();

    void disableListener(String topicName);

    void enableListener(String topicName);

    void clearTopics(List<String> topicNames);

    void clearTopic(String topicName);

    List<String> getMessages(String topicName);

    void sendMessage(String topic, Object request);

}