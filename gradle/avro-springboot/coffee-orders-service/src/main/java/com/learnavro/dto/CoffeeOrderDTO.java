package com.learnavro.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learnavro.domain.generated.PickUp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoffeeOrderDTO {

    private String id;

    @NotBlank(message = "coffeeOrder.name is mandatory")
    private String name;
    private String nickName;

    @NotNull(message = "coffeeOrder.store is mandatory")
    @Valid
    private StoreDTO store;

    @NotNull(message = "coffeeOrder.orderLineItems is mandatory")
    @JsonProperty("orderLineItems")
    private List<@Valid OrderLineItemDTO> orderLineItems;

    @NotNull(message = "coffeeOrder.pickUp is mandatory")
    @JsonProperty("pickUp")
    private PickUp pickUp;

    @NotNull
    private LocalDateTime orderedTime;

    private String status;

}
