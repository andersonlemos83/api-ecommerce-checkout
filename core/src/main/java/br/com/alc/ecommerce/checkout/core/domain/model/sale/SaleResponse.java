package br.com.alc.ecommerce.checkout.core.domain.model.sale;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse implements Serializable {

    private SaleStatus status;
    private LocalDateTime date;

}