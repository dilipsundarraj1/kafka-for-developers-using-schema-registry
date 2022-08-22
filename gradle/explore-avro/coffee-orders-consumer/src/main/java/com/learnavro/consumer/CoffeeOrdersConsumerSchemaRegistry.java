package com.learnavro.consumer;

import com.learnavro.deserializer.CustomAvroDeserializer;
import com.learnavro.domain.generated.CoffeeOrder;
import com.learnavro.domain.generated.CoffeeUpdateEvent;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class CoffeeOrdersConsumerSchemaRegistry {

    private static final Logger log = LoggerFactory.getLogger(CoffeeOrdersConsumerSchemaRegistry.class);
    private static final String COFFEE_ORDERS_TOPIC = "coffee-orders-sr";

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "coffee.consumer7");
        //props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        //props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomAvroDeserializer.class.getName());
        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

      //  KafkaConsumer<OrderId, GenericRecord> consumer = new KafkaConsumer<>(props);
        KafkaConsumer<String, CoffeeOrder> consumer = new KafkaConsumer<>(props);
        //KafkaConsumer<OrderId, CoffeeOrder> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList(COFFEE_ORDERS_TOPIC));
        log.info("Consumer Started");
        while(true) {
           // ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofMillis(100));
           // ConsumerRecords<OrderId, GenericRecord> records = consumer.poll(Duration.ofMillis(100));
            try{
                ConsumerRecords<String, CoffeeOrder> records = consumer.poll(Duration.ofMillis(100));
                //ConsumerRecords<OrderId, CoffeeOrder> records = consumer.poll(Duration.ofMillis(100));
                // for(ConsumerRecord<String, GenericRecord> record : records) {
                //  for(ConsumerRecord<OrderId, GenericRecord> record : records) {
                for(ConsumerRecord<String, CoffeeOrder> record : records) {
                    //for(ConsumerRecord<OrderId, CoffeeOrder> record : records) {
                    //     GenericRecord coffeeOrder =record.value();
                    CoffeeOrder coffeeOrder =record.value();
                    log.info("Consumed message: \n" + record.key() + " : " + coffeeOrder.toString());

//                    var genericRecord = record.value();
//
//                    if(genericRecord instanceof CoffeeOrder){
//                        var coffeeOrder =(CoffeeOrder) genericRecord;
//                        log.info("coffeeOrder : {}", coffeeOrder);
//                    }else if (genericRecord instanceof CoffeeUpdateEvent){
//                        var coffeeOrderUpdateEvent =(CoffeeUpdateEvent) genericRecord;
//                        log.info("coffeeOrderUpdateEvent : {}", coffeeOrderUpdateEvent);
//                    }
                }
            }
            catch (Exception e){
                log.error("Exception is : {} ", e.getMessage(), e);
            }finally {
                consumer.commitSync();
                log.info("Committed the offset catch");
            }

        }
    }
}
