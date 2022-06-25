package com.learnavro.consumer;

import com.learnavro.Greeting;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Properties;

public class GreetingConsumer {

    private static final Logger log = LoggerFactory.getLogger(GreetingConsumer.class);
    private static final String GREETING_TOPIC = "greeting";

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "greeting.consumer2");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());

        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singletonList(GREETING_TOPIC));
        log.info("Consumer Started");
        while(true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(100));

            for(ConsumerRecord<String, byte[]> record : records) {

                try{
                    Greeting greeting = decodeAvroGreeting(record.value());
                    log.info("Consumed message, key : {} , value : {} ", record.key() , greeting.toString());

                    //log.info("Zone Ids : " + ZoneId.SHORT_IDS);
                    var utcDateTime = LocalDateTime.ofInstant(greeting.getCreatedDateTime(),ZoneOffset.UTC);
                    var cstDateTime = LocalDateTime.ofInstant(greeting.getCreatedDateTime(), ZoneId.of("America/Chicago"));

                    log.info("utcDateTime : {} , cstDateTime : {} " , utcDateTime,  cstDateTime);
                }catch (Exception e){
                    log.error("Exception is : {}", e.getMessage(), e);
                }
            }
        }
    }

    public static Greeting decodeAvroGreeting(byte[] array) throws IOException {
        return Greeting.fromByteBuffer(ByteBuffer.wrap(array));
    }
}
