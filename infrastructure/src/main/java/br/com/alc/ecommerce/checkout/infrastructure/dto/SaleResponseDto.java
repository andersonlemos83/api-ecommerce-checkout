package br.com.alc.ecommerce.checkout.infrastructure.dto;

import br.com.alc.ecommerce.checkout.core.domain.model.SaleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "Response sale status", example = "IN_PROCESSING", required = true)
    private SaleStatus status;

    @ApiModelProperty(value = "Response sale date", example = "2021-10-25T20:08:56.194", required = true)
    private LocalDateTime date;

}