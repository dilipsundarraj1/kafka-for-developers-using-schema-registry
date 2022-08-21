package com.learnavro.dto;

import com.learnavro.domain.generated.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoffeeOrderUpdateDTO {
    private OrderStatus orderStatus;
}