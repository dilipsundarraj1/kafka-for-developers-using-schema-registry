package com.learnavro.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreDTO {
    @NotNull(message = "coffeeOrder.store.storeId is mandatory")
    private Integer storeId;

    @Valid
    @NotNull(message = "coffeeOrder.store.address is mandatory")
    private AddressDTO address;
}
