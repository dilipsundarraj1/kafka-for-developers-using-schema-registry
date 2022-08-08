package com.learnavro.service;

import com.learnavro.domain.generated.Address;
import com.learnavro.domain.generated.CoffeeOrder;
import com.learnavro.domain.generated.OrderLineItem;
import com.learnavro.domain.generated.Store;
import com.learnavro.dto.CoffeeOrderDTO;
import com.learnavro.producer.CoffeeOrderProducer;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CoffeeOrderService {

    CoffeeOrderProducer coffeeOrderProducer;

    public CoffeeOrderService(CoffeeOrderProducer coffeeOrderProducer) {
        this.coffeeOrderProducer = coffeeOrderProducer;
    }

    public CoffeeOrderDTO newOrder(CoffeeOrderDTO coffeeOrderDTO) {
        var coffeeOrderAvro = mapToCoffeeOrder(coffeeOrderDTO);
        coffeeOrderDTO.setId(coffeeOrderAvro.getId().toString());
        coffeeOrderProducer.sendMessage(coffeeOrderAvro);
        return coffeeOrderDTO;
    }

    private CoffeeOrder mapToCoffeeOrder(CoffeeOrderDTO coffeeOrderDTO) {

        Store store = getStore(coffeeOrderDTO);

        var orderLineItems = buildOrderLineItems(coffeeOrderDTO);

        return CoffeeOrder.newBuilder()
                .setId(UUID.randomUUID())
                .setName(coffeeOrderDTO.getName())
                .setStore(store)
                .setOrderLineItems(orderLineItems)
                .setStatus(coffeeOrderDTO.getStatus())
                .setOrderedTime(Instant.now())
                .setPickUp(coffeeOrderDTO.getPickUp())
                .setStatus(coffeeOrderDTO.getStatus())
                .build();

    }

    private List<OrderLineItem> buildOrderLineItems(CoffeeOrderDTO coffeeOrderDTO) {

        return coffeeOrderDTO.getOrderLineItems()
                .stream().map(orderLineItem ->
                        new OrderLineItem(
                                orderLineItem.getName(),
                                orderLineItem.getSize(),
                                orderLineItem.getQuantity(),
                                orderLineItem.getCost()
                        )
                )
                .collect(Collectors.toList());
    }

    private Store getStore(CoffeeOrderDTO coffeeOrderDTO) {
        var storeDTO = coffeeOrderDTO.getStore();

        var store = new Store(storeDTO.getStoreId(),
                new Address(storeDTO.getAddress().getAddressLine1(),
                        storeDTO.getAddress().getCity(),
                        storeDTO.getAddress().getState(),
                        storeDTO.getAddress().getCountry(),
                        storeDTO.getAddress().getZip()
                ));
        return store;
    }
}
