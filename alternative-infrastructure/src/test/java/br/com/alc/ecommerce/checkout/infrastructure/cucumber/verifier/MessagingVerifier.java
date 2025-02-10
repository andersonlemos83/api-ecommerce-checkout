package br.com.alc.ecommerce.checkout.infrastructure.cucumber.verifier;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.messaging.MessagingDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.helper.fixture.JsonFixture;
import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.KafkaManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.*;

@Component
@AllArgsConstructor
public class MessagingVerifier {

    private final KafkaManager kafkaManager;

    public void verify(List<MessagingDataTable> messagingDataTableList) {
        messagingDataTableList.forEach(this::verify);
    }

    public void verify(MessagingDataTable messagingDataTable) {
        String expectedJson = getExpectedJson(messagingDataTable);
        String returnedJson = getTopicJson(messagingDataTable);

        String errorMessage = MessageFormat.format("Expected message should be published in the topic {0}", messagingDataTable.getTopicName());
        assertNotNull(errorMessage, expectedJson);
        assertEquals(errorMessage, expectedJson, returnedJson);
    }

    public void verifyEmptyTopics(List<String> topics) {
        topics.forEach(this::verifyEmptyTopic);
    }

    public void verifyEmptyTopic(String topicName) {
        List<String> messages = kafkaManager.getMessages(topicName);
        String errorMessage = MessageFormat.format("No message should be published in the topic {0}", topicName);
        assertTrue(errorMessage, messages.isEmpty());
    }

    private String getExpectedJson(MessagingDataTable messagingDataTable) {
        return JsonFixture.oneJson(messagingDataTable);
    }

    private String getTopicJson(MessagingDataTable messagingDataTable) {
        List<String> messages = kafkaManager.getMessages(messagingDataTable.getTopicName());
        return Optional.ofNullable(messages)
                .filter(m -> !m.isEmpty())
                .map(m -> m.stream().collect(joining("; ", "", "")))
                .orElse(null);
    }
}