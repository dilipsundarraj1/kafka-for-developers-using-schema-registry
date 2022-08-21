package com.learnavro.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
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
    //public void onMessage(ConsumerRecord<String, CoffeeOrder> consumerRecord) {
    public void onMessage(ConsumerRecord<String, GenericRecord> consumerRecord) {

        log.info("ConsumerRecord key: {} , value: {} ", consumerRecord.key(), consumerRecord.value());
    }

}
