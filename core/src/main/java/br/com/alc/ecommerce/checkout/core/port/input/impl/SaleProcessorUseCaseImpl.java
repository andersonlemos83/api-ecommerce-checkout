package br.com.alc.ecommerce.checkout.core.port.input.impl;

import br.com.alc.ecommerce.checkout.core.domain.authorize.AuthorizeSaleResponse;
import br.com.alc.ecommerce.checkout.core.domain.callback.SaleCallbackRequest;
import br.com.alc.ecommerce.checkout.core.domain.order.SaleOrder;
import br.com.alc.ecommerce.checkout.core.domain.sale.SaleRequest;
import br.com.alc.ecommerce.checkout.core.port.input.SaleProcessorUseCase;
import br.com.alc.ecommerce.checkout.core.port.output.MostRecentSaleOrderFinderOutPort;
import br.com.alc.ecommerce.checkout.core.port.output.SaleCallbackIntegrateOutPort;
import br.com.alc.ecommerce.checkout.core.port.output.SaleOrderInserterOutPort;
import br.com.alc.ecommerce.checkout.core.service.authorize.SaleAuthorizerService;
import br.com.alc.ecommerce.checkout.core.service.validator.SaleValidatorService;
import br.com.alc.ecommerce.checkout.core.service.watch.WatchService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

import static br.com.alc.ecommerce.checkout.core.domain.sale.SaleStatus.*;
import static br.com.alc.ecommerce.checkout.core.util.ObjectMapperUtil.generateJson;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@Log4j2
@AllArgsConstructor
public final class SaleProcessorUseCaseImpl implements SaleProcessorUseCase {

    private static final String OUTGOING_FROM_SALE_PROCESSOR_USE_CASE_IMPL = "Outgoing from SaleProcessorUseCaseImpl: {} - {}";

    private final MostRecentSaleOrderFinderOutPort mostRecentSaleOrderFinderOutPort;
    private final SaleValidatorService saleValidatorService;
    private final SaleOrderInserterOutPort saleOrderInserterOutPort;
    private final SaleAuthorizerService saleAuthorizerService;
    private final SaleCallbackIntegrateOutPort saleCallbackIntegrateOutPort;
    private final WatchService watchService;

    @Override
    public void execute(SaleRequest saleRequest) {
        log.info("Incoming into SaleProcessorUseCaseImpl: {}", generateJson(saleRequest));
        Optional<SaleOrder> saleOrderOptional = mostRecentSaleOrderFinderOutPort.execute(saleRequest.getOrderNumber());

        if (saleOrderOptional.filter(SaleOrder::isProcessed).isPresent()) {
            integrateSaleCallback(saleOrderOptional.get());
            log.info(OUTGOING_FROM_SALE_PROCESSOR_USE_CASE_IMPL, saleOrderOptional.get().getStatus(), generateJson(saleOrderOptional.get()));
            return;
        }

        if (saleOrderOptional.filter(SaleOrder::isInProcessing).isPresent()) {
            log.info(OUTGOING_FROM_SALE_PROCESSOR_USE_CASE_IMPL, saleOrderOptional.get().getStatus(), generateJson(saleOrderOptional.get()));
            return;
        }

        try {
            saleValidatorService.execute(saleRequest);

            SaleOrder processingSaleOrdersaleOrder = buildProcessingSaleOrder(saleRequest);
            saleOrderInserterOutPort.execute(processingSaleOrdersaleOrder);

            AuthorizeSaleResponse authorizeSaleResponse = saleAuthorizerService.execute(saleRequest);

            SaleOrder processedSaleOrder = buildProcessedSaleOrder(saleRequest, authorizeSaleResponse);
            saleOrderInserterOutPort.execute(processedSaleOrder);

            integrateSaleCallback(processedSaleOrder);
            log.info(OUTGOING_FROM_SALE_PROCESSOR_USE_CASE_IMPL, processedSaleOrder.getStatus(), generateJson(processedSaleOrder));

        } catch (Exception exception) {
            log.error("Error in the SaleProcessorUseCaseImpl: {}", getMessage(exception), exception);
            SaleOrder errorSaleOrder = buildErrorSaleOrder(saleRequest, exception);
            saleOrderInserterOutPort.execute(errorSaleOrder);

            integrateSaleCallback(errorSaleOrder);
            log.info(OUTGOING_FROM_SALE_PROCESSOR_USE_CASE_IMPL, errorSaleOrder.getStatus(), generateJson(errorSaleOrder));
        }
    }

    private SaleOrder buildProcessingSaleOrder(SaleRequest saleRequest) {
        SaleOrder.SaleOrderBuilder saleOrderBuilder = buildSaleOrder(saleRequest);
        return saleOrderBuilder
                .status(IN_PROCESSING)
                .build();
    }

    private SaleOrder buildErrorSaleOrder(SaleRequest saleRequest, Exception exception) {
        SaleOrder.SaleOrderBuilder saleOrderBuilder = buildSaleOrder(saleRequest);
        return saleOrderBuilder
                .status(ERROR)
                .errorReason(StringUtils.truncate(exception.getMessage(), 999))
                .build();
    }

    private SaleOrder buildProcessedSaleOrder(SaleRequest saleRequest, AuthorizeSaleResponse authorizeSaleResponse) {
        SaleOrder.SaleOrderBuilder saleOrderBuilder = buildSaleOrder(saleRequest);
        return saleOrderBuilder
                .invoiceKey(authorizeSaleResponse.getInvoiceKey())
                .invoiceNumber(authorizeSaleResponse.getInvoiceNumber())
                .issuanceDate(authorizeSaleResponse.getIssuanceDate())
                .invoiceBase64(authorizeSaleResponse.getInvoiceBase64())
                .status(PROCESSED)
                .build();
    }

    private SaleOrder.SaleOrderBuilder buildSaleOrder(SaleRequest saleRequest) {
        return SaleOrder.builder()
                .channelCode(saleRequest.getChannelCode())
                .companyCode(saleRequest.getCompanyCode())
                .storeCode(saleRequest.getStoreCode())
                .pos(saleRequest.getPos())
                .orderNumber(saleRequest.getOrderNumber())
                .totalValue(saleRequest.getTotalValue())
                .freightValue(saleRequest.getFreightValue())
                .createdDate(watchService.nowLocalDateTime())
                .updatedDate(watchService.nowLocalDateTime());
    }

    private void integrateSaleCallback(SaleOrder saleOrder) {
        SaleCallbackRequest saleCallbackRequest = buildSaleCallbackRequest(saleOrder);
        saleCallbackIntegrateOutPort.execute(saleCallbackRequest);
    }

    private SaleCallbackRequest buildSaleCallbackRequest(SaleOrder saleOrder) {
        return SaleCallbackRequest.builder()
                .orderNumber(saleOrder.getOrderNumber())
                .invoiceKey(saleOrder.getInvoiceKey())
                .invoiceNumber(saleOrder.getInvoiceNumber())
                .issuanceDate(saleOrder.getIssuanceDate())
                .invoiceBase64(saleOrder.getInvoiceBase64())
                .status(saleOrder.getStatus())
                .errorReason(saleOrder.getErrorReason())
                .build();
    }
}