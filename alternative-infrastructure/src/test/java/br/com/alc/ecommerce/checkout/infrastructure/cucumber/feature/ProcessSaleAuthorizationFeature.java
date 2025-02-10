package br.com.alc.ecommerce.checkout.infrastructure.cucumber.feature;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale.SaleRequestDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.dto.sale.SaleRequestDto;
import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.KafkaManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessSaleAuthorizationFeature {

    private final KafkaManager kafkaManager;
    private final ModelMapper modelMapper;

    private final String authorizeSaleTopic;

    @Autowired
    public ProcessSaleAuthorizationFeature(KafkaManager kafkaManager,
                                           ModelMapper modelMapper,
                                           @Value("${spring.kafka.authorize-sale-topic}") String authorizeSaleTopic) {
        this.kafkaManager = kafkaManager;
        this.modelMapper = modelMapper;
        this.authorizeSaleTopic = authorizeSaleTopic;
    }

    public void execute(SaleRequestDataTable saleRequestDataTable) {
        kafkaManager.enableListener(authorizeSaleTopic);
        SaleRequestDto saleRequestDto = modelMapper.map(saleRequestDataTable, SaleRequestDto.class);
        kafkaManager.sendMessage(authorizeSaleTopic, saleRequestDto);
    }
}