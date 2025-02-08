package br.com.alc.ecommerce.checkout.infrastructure.dto.sale;

import br.com.alc.ecommerce.checkout.core.domain.sale.SaleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class SaleResponseDto implements Serializable {

    @Schema(description = "Response sale status", example = "IN_PROCESSING")
    private SaleStatus status;

    @Schema(description = "Response sale date", example = "2021-10-25T20:08:56.194")
    private LocalDateTime date;

}