package br.com.alc.ecommerce.checkout.infrastructure.cucumber.feature;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.SaleRequestDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.manager.RabbitMqManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessSaleAuthorizationFeature {

    private final RabbitMqManager rabbitMqManager;

    private final String saleExchange;
    private final String authorizeSaleQueue;

    @Autowired
    public ProcessSaleAuthorizationFeature(RabbitMqManager rabbitMqManager,
                                           @Value("${spring.rabbitmq.sale-exchange}") String saleExchange,
                                           @Value("${spring.rabbitmq.authorize-sale-queue}") String authorizeSaleQueue) {
        this.rabbitMqManager = rabbitMqManager;
        this.saleExchange = saleExchange;
        this.authorizeSaleQueue = authorizeSaleQueue;
    }

    public void execute(SaleRequestDataTable saleRequestDataTable) {
        rabbitMqManager.enableListener(authorizeSaleQueue);
        rabbitMqManager.sendMessage(saleExchange, authorizeSaleQueue, saleRequestDataTable);
    }
}