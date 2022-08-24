package com.learnavro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnavro.domain.generated.OrderLineItem;
import com.learnavro.domain.generated.PickUp;
import com.learnavro.domain.generated.Size;
import com.learnavro.dto.AddressDTO;
import com.learnavro.dto.CoffeeOrderDTO;
import com.learnavro.dto.OrderLineItemDTO;
import com.learnavro.dto.StoreDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class CoffeeOrdersServiceApplicationTests {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() throws JsonProcessingException {

        var address = new AddressDTO("1234 street",
                "Chicago",
                "Illinois",
                "USA",
                "11244");

        var orderLineItem = OrderLineItemDTO.builder()
                .name("Latte")
                .size(Size.MEDIUM)
                .quantity(1)
                .cost(new BigDecimal(3.99))
                .build();

        var coffeeOrderDTO = new CoffeeOrderDTO(
                UUID.randomUUID().toString(),
                "Dilip Sundarraj",
                "",
                new StoreDTO(123,address )
                , List.of(orderLineItem)
                , PickUp.IN_STORE,
                LocalDateTime.now(),
                "NEW"
        );

        var coffeeOrderJSON =objectMapper.writeValueAsString(coffeeOrderDTO);
        System.out.println("coffeeOrderJSON : "+ coffeeOrderJSON);
    }

}
