package br.com.alc.ecommerce.checkout.core.application.port.input.impl;

import br.com.alc.ecommerce.checkout.core.application.port.input.SaleProcessorUseCase;
import br.com.alc.ecommerce.checkout.core.application.port.output.MostRecentSaleOrderFinderOutPort;
import br.com.alc.ecommerce.checkout.core.application.port.output.SaleCallbackIntegrateOutPort;
import br.com.alc.ecommerce.checkout.core.application.port.output.SaleOrderInserterOutPort;
import br.com.alc.ecommerce.checkout.core.application.service.SaleAuthorizerService;
import br.com.alc.ecommerce.checkout.core.application.service.validator.SaleValidatorService;
import br.com.alc.ecommerce.checkout.core.application.service.watch.WatchService;
import br.com.alc.ecommerce.checkout.core.domain.model.SaleAuthorizeResponse;
import br.com.alc.ecommerce.checkout.core.domain.model.order.SaleOrder;
import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleRequest;
import lombok.AllArgsConstructor;

import java.util.Optional;

import static br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleStatus.ERROR;
import static br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleStatus.IN_PROCESSING;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;

@AllArgsConstructor
public class SaleProcessorUseCaseImpl implements SaleProcessorUseCase {

    private final MostRecentSaleOrderFinderOutPort mostRecentSaleOrderFinderOutPort;
    private final SaleValidatorService saleValidatorService;
    private final SaleOrderInserterOutPort saleOrderInserterOutPort;
    private final SaleAuthorizerService saleAuthorizerService;
    private final SaleCallbackIntegrateOutPort saleCallbackIntegrateOutPort;
    private final WatchService watchService;

    @Override
    public void execute(SaleRequest saleRequest) {
        Optional<SaleOrder> saleOrderOptional = mostRecentSaleOrderFinderOutPort.execute(saleRequest.getNumberOrder());

        if (saleOrderOptional.filter(SaleOrder::isProcessed).isPresent()) {
            saleCallbackIntegrateOutPort.execute(saleOrderOptional.get());
            return;
        }

        if (!saleOrderOptional.isPresent() || saleOrderOptional.get().isError()) {
            try {
                saleValidatorService.execute(saleRequest);

                SaleOrder prossingSaleOrdersaleOrder = buildProssingSaleOrder(saleRequest);
                saleOrderInserterOutPort.execute(prossingSaleOrdersaleOrder);

                SaleAuthorizeResponse saleAuthorizeResponse = saleAuthorizerService.execute(saleRequest);

                SaleOrder processedSaleOrder = buildProcessedSaleOrder(saleRequest, saleAuthorizeResponse);
                saleOrderInserterOutPort.execute(processedSaleOrder);

                saleCallbackIntegrateOutPort.execute(processedSaleOrder);

            } catch (Exception exception) {
                SaleOrder saleOrder = buildErrorSaleOrder(saleRequest, exception);
                saleOrderInserterOutPort.execute(saleOrder);
                saleCallbackIntegrateOutPort.execute(saleOrder);
            }
        }
    }

    private SaleOrder buildProssingSaleOrder(SaleRequest saleRequest) {
        SaleOrder.SaleOrderBuilder saleOrderBuilder = buildSaleOrder(saleRequest);
        return saleOrderBuilder
                .status(IN_PROCESSING)
                .build();
    }

    private SaleOrder buildErrorSaleOrder(SaleRequest saleRequest, Exception exception) {
        SaleOrder.SaleOrderBuilder saleOrderBuilder = buildSaleOrder(saleRequest);
        return saleOrderBuilder
                .status(ERROR)
                .errorReason(getRootCauseMessage(exception))
                .build();
    }

    private SaleOrder buildProcessedSaleOrder(SaleRequest saleRequest, SaleAuthorizeResponse saleAuthorizeResponse) {
        SaleOrder.SaleOrderBuilder saleOrderBuilder = buildSaleOrder(saleRequest);
        return saleOrderBuilder
                .invoiceKey(saleAuthorizeResponse.getInvoiceKey())
                .invoiceNumber(saleAuthorizeResponse.getInvoiceNumber())
                .issuanceDate(saleAuthorizeResponse.getIssuanceDate())
                .invoiceBase64(saleAuthorizeResponse.getInvoiceBase64())
                .status(IN_PROCESSING)
                .build();
    }

    private SaleOrder.SaleOrderBuilder buildSaleOrder(SaleRequest saleRequest) {
        return SaleOrder.builder()
                .channelCode(saleRequest.getChannelCode())
                .companyCode(saleRequest.getCompanyCode())
                .storeCode(saleRequest.getStoreCode())
                .pos(saleRequest.getPos())
                .numberOrder(saleRequest.getNumberOrder())
                .totalAmount(saleRequest.getTotalAmount())
                .freightAmount(saleRequest.getFreightAmount())
                .createdDate(watchService.nowLocalDateTime())
                .updatedDate(watchService.nowLocalDateTime());
    }
}