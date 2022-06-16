package com.learnavro.producer;

import com.learnavro.Greeting;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class GreetingProducer {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingProducer.class);
    private static final String GREETING_TOPIC = "greeting";
    private static final String GREETING_STRING_TOPIC = "greeting_string";

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(props);

        Thread shutdownHook = new Thread(producer::close);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        Greeting greeting = buildGreeting("Hello, Schema Registry!");

        byte[] value = greeting.toByteBuffer().array();

        ProducerRecord<String, byte[]> producerRecord =
                new ProducerRecord<>(GREETING_TOPIC, value);
        var recordMetaData = producer.send(producerRecord).get();
        System.out.println("recordMetaData : " + recordMetaData);

        stringProducer(props);
    }

    private static void stringProducer(Properties props) throws IOException, ExecutionException, InterruptedException {

        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        Thread shutdownHook = new Thread(producer::close);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        Greeting greeting = buildGreeting("Hello, Schema Registry!");

        String value = greeting.toByteBuffer().toString();

        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>(GREETING_STRING_TOPIC, value);
        var recordMetaData = producer.send(producerRecord).get();
        System.out.println("recordMetaData using String: " + recordMetaData);

    }

    private static Greeting buildGreeting(String message) {

        return Greeting.newBuilder()
                .setGreeting(message)
                .build();
    }

}
