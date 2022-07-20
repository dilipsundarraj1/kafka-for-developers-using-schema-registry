package com.learnavro.deserializer;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomAvroDeserializer extends KafkaAvroDeserializer {
    private static final Logger log = LoggerFactory.getLogger(CustomAvroDeserializer.class);

    @Override
    public Object deserialize(String s, byte[] bytes) {
        try{
            return super.deserialize(s, bytes);
        }catch (Exception e){
            log.error("Exception in deserialization : {} ", e.getMessage(), e);
            return null;
        }

    }

    @Override
    public Object deserialize(String s, byte[] bytes, Schema readerSchema) {

        try{
            return super.deserialize(s, bytes, readerSchema);
        }catch (Exception e){
            log.error("Exception in deserialization with Schema: {} ", e.getMessage(), e);
            return null;
        }
    }
}
