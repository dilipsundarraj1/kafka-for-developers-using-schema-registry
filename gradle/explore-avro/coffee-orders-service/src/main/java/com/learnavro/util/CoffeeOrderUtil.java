package com.learnavro.util;

import com.learnavro.domain.generated.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class CoffeeOrderUtil {

    public static CoffeeOrder buildNewCoffeeOrder(){
        var orderId=   OrderId.newBuilder()
                .setId(randomId())
                .build();

        return CoffeeOrder.newBuilder()
                .setId(orderId)
               .setName("Dilip Sundarraj")
                //.setNickName("DS")
                //.setFullName("Dilip Sundarraj")
                .setStore(generateStore())
                .setOrderLineItems(generateOrderLineItems())
                .setOrderedTime(Instant.now())
                 .setPickUp(PickUp.IN_STORE)
               // .setPickUpType(PickUp.IN_STORE)
                .build();


    }

//    public static CoffeeOrderOld buildNewCoffeeOrderV2(){
//
//        return CoffeeOrderOld.newBuilder()
//                .setId(randomId())
//                .setName("Chicago 1234")
//                .setStore(generateStore())
//                .setOrderLineItems(generateOrderLineItems())
//                .build();
//
//
//    }

    private static List<OrderLineItem> generateOrderLineItems() {

        var orderLineItem = OrderLineItem.newBuilder()
                .setName("Caffe Latte")
                .setQuantity(1)
                .setSize(Size.MEDIUM)
                .setCost(BigDecimal.valueOf(3.99))
                .build();

        return List.of(orderLineItem);

    }

    private static Store generateStore(){

        return  Store.newBuilder()
                .setId(randomId())
                .setAddress(buildAddress())
                .build();
    }

    private static Address buildAddress() {

        return Address.newBuilder()
                .setAddressLine1("1234 Address Line 1")
                .setCity("Chicago")
                .setStateProvince("IL")
                .setZip("12345")
                .build();

    }

    public static int randomId(){
         Random random = new Random();
         return random.nextInt(1000);
    }
}
