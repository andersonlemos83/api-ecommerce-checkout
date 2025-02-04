package br.com.alc.ecommerce.checkout.infrastructure.cucumber.context;

import br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.order.SaleOrderDataTable;
import br.com.alc.ecommerce.checkout.infrastructure.helper.repository.SaleOrderRepositoryHelper;
import br.com.alc.ecommerce.checkout.infrastructure.persistence.entity.SaleOrderEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SaleOrderContext {

    private final SaleOrderRepositoryHelper saleOrderRepositoryHelper;

    public void insert(List<SaleOrderDataTable> orderDataTableList) {
        List<SaleOrderEntity> saleOrderEntityList = orderDataTableList.stream().map(this::buildSaleOrderEntity).toList();
        saleOrderRepositoryHelper.saveAll(saleOrderEntityList);
    }

    private SaleOrderEntity buildSaleOrderEntity(SaleOrderDataTable saleOrderDataTable) {
        SaleOrderEntity saleOrderEntity = SaleOrderEntity.builder().build();
        BeanUtils.copyProperties(saleOrderDataTable, saleOrderEntity);
        return saleOrderEntity;
    }
}