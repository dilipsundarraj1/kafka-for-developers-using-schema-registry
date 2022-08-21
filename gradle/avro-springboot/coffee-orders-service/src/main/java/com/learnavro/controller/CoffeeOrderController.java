package com.learnavro.controller;

import com.learnavro.dto.CoffeeOrderDTO;
import com.learnavro.dto.CoffeeOrderUpdateDTO;
import com.learnavro.service.CoffeeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/coffee_orders")
@Validated
@Slf4j
public class CoffeeOrderController{
    private CoffeeOrderService coffeeOrderService;

    public CoffeeOrderController(CoffeeOrderService coffeeOrderService) {
        this.coffeeOrderService = coffeeOrderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrderDTO newOrder(@Valid @RequestBody CoffeeOrderDTO coffeeOrderDTO){
        log.info("Received Request for an order");
        return coffeeOrderService.newOrder(coffeeOrderDTO);
    }

    @PutMapping("/{order_id}")
    @ResponseStatus(HttpStatus.OK)
    public CoffeeOrderUpdateDTO UpdateCoffeeOrder(@PathVariable("order_id") String orderId
            ,@Valid @RequestBody CoffeeOrderUpdateDTO coffeeOrderUpdateDTO){
        log.info("Received Request for an order");
        return coffeeOrderService.updateOrder(orderId, coffeeOrderUpdateDTO);
    }
}
