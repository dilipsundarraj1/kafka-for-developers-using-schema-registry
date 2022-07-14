package com.learnavro.consumer;

import com.learnavro.domain.generated.CoffeeOrder;
import com.learnavro.domain.generated.OrderId;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoffeeOrdersConsumerSchemaRegistry {

    private static final Logger log = LoggerFactory.getLogger(CoffeeOrdersConsumerSchemaRegistry.class);
    private static final String COFFEE_ORDERS_TOPIC = "coffee-orders-sr";
    static  final Pattern offsetPattern = Pattern.compile("\\w*offset*\\w[ ]\\d+");
    static final Pattern partitionPattern = Pattern.compile("\\w*" + COFFEE_ORDERS_TOPIC + "*\\w[-]\\d+");

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "coffee.consumer7");
        //props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
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
                }
            }
            catch (SerializationException e) {
                log.error("SerializationException is : {} ", e.getMessage(), e);
                String text = e.getMessage();
                Matcher mPart = partitionPattern.matcher(text);
                Matcher mOff = offsetPattern.matcher(text);
                mPart.find();
                Integer partition = Integer.parseInt(mPart.group().replace(COFFEE_ORDERS_TOPIC + "-", ""));
                mOff.find();
                Long offset = Long.parseLong(mOff.group().replace("offset ", ""));
                consumer.seek(new TopicPartition(COFFEE_ORDERS_TOPIC, partition), offset + 1);

            }
            catch (Exception e){
                log.error("Exception is : {} ", e.getMessage(), e);
                consumer.commitSync();
                log.info("Committed the offset catch");
            }finally {
                consumer.commitSync();
//                consumer.commitAsync((map, e) -> {
//                    log.info("Committed the offset map : {} , e : {} ",map, e );
//                });
            }


        }
    }
}
