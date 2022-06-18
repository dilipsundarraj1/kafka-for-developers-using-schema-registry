package com.learnavro.consumer;

import com.learnavro.domain.generated.CoffeeOrder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class CoffeeOrdersConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(CoffeeOrdersConsumer.class);
    private static final String COFFEE_ORDERS_TOPIC = "coffee-orders";

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "coffee.consumer2");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());

        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);

        Thread shutdownHook = new Thread(consumer::close);
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        consumer.subscribe(Collections.singletonList(COFFEE_ORDERS_TOPIC));
        System.out.println("Consumer Started");
        while(true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(100));
            for(ConsumerRecord<String, byte[]> record : records) {
                CoffeeOrder coffeeOrder = decodeCoffeeOrder(record.value());
                System.out.println("Consumed message: \n" + record.key() + " : " + coffeeOrder.toString());
            }
        }
    }

    public static CoffeeOrder decodeCoffeeOrder(byte[] array) throws IOException {
        return CoffeeOrder.fromByteBuffer(ByteBuffer.wrap(array));
    }
}
