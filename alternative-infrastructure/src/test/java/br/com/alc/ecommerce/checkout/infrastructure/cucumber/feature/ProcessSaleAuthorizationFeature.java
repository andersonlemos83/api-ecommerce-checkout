package br.com.alc.ecommerce.checkout.infrastructure.cucumber.feature;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.SaleRequestDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.KafkaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessSaleAuthorizationFeature {

    private final KafkaManager kafkaManager;

    private final String authorizeSaleTopic;

    @Autowired
    public ProcessSaleAuthorizationFeature(KafkaManager kafkaManager,
                                           @Value("${spring.kafka.authorize-sale-topic}") String authorizeSaleTopic) {
        this.kafkaManager = kafkaManager;
        this.authorizeSaleTopic = authorizeSaleTopic;
    }

    public void execute(SaleRequestDataTable saleRequestDataTable) {
        kafkaManager.enableListener(authorizeSaleTopic);
        kafkaManager.sendMessage(authorizeSaleTopic, saleRequestDataTable);
    }
}