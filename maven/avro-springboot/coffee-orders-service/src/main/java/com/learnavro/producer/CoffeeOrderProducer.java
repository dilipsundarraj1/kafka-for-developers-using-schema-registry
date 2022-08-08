package com.learnavro.producer;

import com.learnavro.domain.generated.CoffeeOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class CoffeeOrderProducer {

    KafkaTemplate<String, CoffeeOrder> kafkaTemplate;

    public CoffeeOrderProducer(KafkaTemplate<String, CoffeeOrder> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(CoffeeOrder coffeeOrderAvro) {
        var producerRecord = new ProducerRecord<>("coffee-orders", coffeeOrderAvro.getId().toString(), coffeeOrderAvro);


        ListenableFuture<SendResult<String,CoffeeOrder>> listenableFuture =  kafkaTemplate.send(producerRecord);;
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, CoffeeOrder>>() {

            @Override
            public void onFailure(Throwable ex) {
                handleFailure(coffeeOrderAvro, ex);
            }

            @Override
            public void onSuccess(SendResult<String, CoffeeOrder> result) {
                handleSuccess(coffeeOrderAvro, result);
            }
        });
    }

    private void handleFailure(CoffeeOrder coffeeOrder, Throwable ex) {
        log.error("Error Sending the Message for {} and the exception is {}", coffeeOrder,ex.getMessage(), ex);
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }


    }

    private void handleSuccess(CoffeeOrder coffeeOrder, SendResult<String, CoffeeOrder> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}", coffeeOrder.getId(), coffeeOrder, result.getRecordMetadata().partition());
    }

}
