package com.learnavro.consumer;

import com.learnavro.domain.generated.CoffeeOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CoffeeOrdersConsumer {

    @KafkaListener(
            topics = {"coffee-orders"}
            , autoStartup = "${coffeeOrdersConsumer.startup:true}"
            , groupId = "${spring.kafka.consumer.group-id}")
    public void onMessage(ConsumerRecord<String, CoffeeOrder> consumerRecord) {

        log.info("ConsumerRecord key: {} , value: {} ", consumerRecord.key(), consumerRecord.value());
    }

}
