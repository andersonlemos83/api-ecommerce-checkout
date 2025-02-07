package br.com.alc.ecommerce.checkout.core.domain.sale;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class SaleResponse implements Serializable {

    private SaleStatus status;
    private LocalDateTime date;

}