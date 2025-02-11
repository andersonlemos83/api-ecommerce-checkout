package br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.messaging;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.messaging.MessagingDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.stepdefs.StepDefs;
import br.com.alc.ecommerce.checkout.infrastructure.cucumber.verifier.MessagingVerifier;
import io.cucumber.java.pt.E;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class MessagingStepDefs extends StepDefs {

    private final MessagingVerifier messagingVerifier;

    @E("^deveria publicar o JSON esperado no topico$")
    public void deveriaPublicarOhJsonEsperadoNoTopico(List<MessagingDataTable> messagingDataTableList) {
        messagingVerifier.verify(messagingDataTableList);
    }

    @E("^nao deveria publicar nenhum JSON no topico$")
    public void naoDeveriaPublicarNenhumJsonNoTopico(List<MessagingDataTable> messagingDataTableList) {
        List<String> topics = messagingDataTableList.stream().map(MessagingDataTable::getTopicName).distinct().collect(toList());
        messagingVerifier.verifyEmptyTopics(topics);
    }
}