package com.learnavro.producer;

import com.learnavro.domain.generated.CoffeeOrder;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.confluent.kafka.serializers.subject.RecordNameStrategy;
import io.confluent.kafka.serializers.subject.TopicRecordNameStrategy;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static com.learnavro.util.CoffeeOrderUtil.buildNewCoffeeOrder;

public class CoffeeOrderProducerSchemaRegistry {

    private static final String COFFEE_ORDERS_TOPIC = "coffee-orders-sr";
    private static final Logger log = LoggerFactory.getLogger(CoffeeOrderProducerSchemaRegistry.class);


    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        //props.put(KafkaAvroSerializerConfig.VALUE_SUBJECT_NAME_STRATEGY, RecordNameStrategy.class.getName());
        props.put(KafkaAvroSerializerConfig.VALUE_SUBJECT_NAME_STRATEGY, TopicRecordNameStrategy.class.getName());


        KafkaProducer<String, CoffeeOrder> producer = new KafkaProducer<>(props);
        //KafkaProducer<OrderId, CoffeeOrder> producer = new KafkaProducer<>(props);

        CoffeeOrder coffeeOrder = buildNewCoffeeOrder();
        System.out.println("Coffee Order Sent " + coffeeOrder);

//        byte[] value = coffeeOrder.toByteBuffer().array();

        ProducerRecord<String, CoffeeOrder> producerRecord =
                new ProducerRecord<>(COFFEE_ORDERS_TOPIC, coffeeOrder);
//        ProducerRecord<OrderId, CoffeeOrder> producerRecord =
//                new ProducerRecord<>(COFFEE_ORDERS_TOPIC,coffeeOrder.getId(), coffeeOrder);
        var recordMetaData = producer.send(producerRecord).get();
        log.info("recordMetaData : {}" , recordMetaData);

    }

}
