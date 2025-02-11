package br.com.alc.ecommerce.checkout.infrastructure.messaging.producer.impl;

import br.com.alc.ecommerce.checkout.infrastructure.dto.error.ErrorResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@SuppressWarnings("java:S5786") // Public required for JUnit test suite
@ExtendWith(SpringExtension.class)
public class MessagingProducerImplTest {

    @InjectMocks
    private MessagingProducerImpl messagingProducer;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplateMock;

    @Test
    void givenThatItThrowsAnyExceptionWhenExecutingThePublishMethodThenShouldGenerateAnErrorLog() {
        String topic = "topic";
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder().build();

        doThrow(RuntimeException.class).when(kafkaTemplateMock).send(topic, errorResponseDto);

        messagingProducer.publish(topic, errorResponseDto);

        verify(kafkaTemplateMock, times(1)).send(topic, errorResponseDto);
    }
}