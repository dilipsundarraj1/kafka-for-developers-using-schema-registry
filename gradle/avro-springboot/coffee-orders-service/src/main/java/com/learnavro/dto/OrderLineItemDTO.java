package com.learnavro.dto;

import com.learnavro.domain.generated.Size;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemDTO {
    @NotBlank(message = "coffeeOrder.orderLineItem.name is mandatory")
    private String name;

    @NotNull(message = "coffeeOrder.orderLineItem.size is mandatory")
    private Size size;

    @NotNull(message = "coffeeOrder.orderLineItem.size is mandatory")
    private Integer quantity;

    @NotNull
    private BigDecimal cost;
}
