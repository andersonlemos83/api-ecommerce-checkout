package br.com.alc.ecommerce.checkout.infrastructure.cucumber.datatable.sale;

import br.com.alc.ecommerce.checkout.core.domain.model.sale.SaleStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponseDataTable implements Serializable {

    private SaleStatus status;
    private LocalDateTime date;

}