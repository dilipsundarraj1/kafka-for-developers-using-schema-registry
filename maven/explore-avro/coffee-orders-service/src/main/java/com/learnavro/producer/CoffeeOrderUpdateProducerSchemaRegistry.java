package com.learnavro.producer;

import com.learnavro.domain.generated.CoffeeUpdateEvent;
import com.learnavro.domain.generated.OrderStatus;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.confluent.kafka.serializers.subject.RecordNameStrategy;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class CoffeeOrderUpdateProducerSchemaRegistry {

    private static final String COFFEE_ORDERS_TOPIC = "coffee-orders-sr";


    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        //props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        props.put(KafkaAvroSerializerConfig.VALUE_SUBJECT_NAME_STRATEGY, RecordNameStrategy.class.getName());
        //props.put(KafkaAvroSerializerConfig.VALUE_SUBJECT_NAME_STRATEGY, TopicRecordNameStrategy.class.getName());


        KafkaProducer<String, CoffeeUpdateEvent> producer = new KafkaProducer<>(props);

        //CoffeeUpdateEvent coffeeOrderUpdateEvent = buildCoffeeOrderUpdateEvent(OrderStatus.PROCESSING);
        CoffeeUpdateEvent coffeeOrderUpdateEvent = buildCoffeeOrderUpdateEvent();

//        byte[] value = coffeeOrder.toByteBuffer().array();

        ProducerRecord<String, CoffeeUpdateEvent> producerRecord =
                new ProducerRecord<>(COFFEE_ORDERS_TOPIC, coffeeOrderUpdateEvent.getId().toString(),
                        coffeeOrderUpdateEvent);
        var recordMetaData = producer.send(producerRecord).get();
        System.out.println("recordMetaData : " + recordMetaData);

    }

    private static CoffeeUpdateEvent buildCoffeeOrderUpdateEvent() {


        return CoffeeUpdateEvent.newBuilder()
                .setId(UUID.randomUUID())
                .setStatus(OrderStatus.PROCESSING)
                .build();

    }

}
