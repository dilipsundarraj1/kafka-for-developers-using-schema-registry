package com.learnavro.producer;

import com.learnavro.domain.generated.CoffeeUpdateEvent;
import com.learnavro.domain.generated.OrderId;
import com.learnavro.domain.generated.OrderStatus;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.confluent.kafka.serializers.subject.RecordNameStrategy;
import io.confluent.kafka.serializers.subject.TopicRecordNameStrategy;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static com.learnavro.util.CoffeeOrderUtil.randomId;

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


        KafkaProducer<Integer, CoffeeUpdateEvent> producer = new KafkaProducer<>(props);

        //CoffeeUpdateEvent coffeeOrderUpdateEvent = buildCoffeeOrderUpdateEvent(OrderStatus.PROCESSING);
        CoffeeUpdateEvent coffeeOrderUpdateEvent = buildCoffeeOrderUpdateEvent(OrderStatus.READY_FOR_PICK_UP);

//        byte[] value = coffeeOrder.toByteBuffer().array();

        ProducerRecord<Integer, CoffeeUpdateEvent> producerRecord =
                new ProducerRecord<>(COFFEE_ORDERS_TOPIC, coffeeOrderUpdateEvent.getId(),
                        coffeeOrderUpdateEvent);
        var recordMetaData = producer.send(producerRecord).get();
        System.out.println("recordMetaData : " + recordMetaData);

    }

    private static CoffeeUpdateEvent buildCoffeeOrderUpdateEvent(OrderStatus orderStatus) {


        return CoffeeUpdateEvent.newBuilder()
                .setId(123)
                .setStatus(orderStatus)
                .build();

    }

}
