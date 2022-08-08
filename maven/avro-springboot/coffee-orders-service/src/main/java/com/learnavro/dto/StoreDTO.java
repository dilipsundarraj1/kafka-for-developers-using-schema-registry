package com.learnavro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
